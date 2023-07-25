package com.youngmok.myboard.controller;


import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.domain.CommentDTO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    CommentService SC;

    @Autowired
    FileDAO FDAO;

    // 지정된 게시물의 모든 댓글을 가져오는 메서드
    @ResponseBody
    @GetMapping("/comments") // comments?bno=3333
    public ResponseEntity<List<CommentDTO>> list(Integer bno) {
        logger.info("bno : " + bno);
        List<CommentDTO> list = null;
        try {
            list = SC.getList(bno); // 댓글 리스트를 가져옴
            System.out.println("list = " + list);
            return new ResponseEntity<List<CommentDTO>>(list, HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("댓글을 불러오지 못하였습니다. : {}", e.getMessage(), e);
            return new ResponseEntity<List<CommentDTO>>(HttpStatus.BAD_REQUEST); // 400
        }
    }

    // 댓글을 삭제하는 메서드
    @DeleteMapping("/comments/{cno}")  //  /comments/1?bno=1085  #{}로 가져올떄는 PathVariable
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno) {
        logger.info("bno : " + bno+"  "+"cno = " + cno);
        try {
            int rowCnt = SC.remove(cno, bno);  // 해당하는 댓글을 삭제함
            if (rowCnt > 0) {
                logger.info("댓글삭제 성공!");
            } else {
                throw new Exception();
            }
            return new ResponseEntity<>("DEL_OK", HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("댓글을 삭제하지 못하였습니다. : {}", e.getMessage(), e);
            return new ResponseEntity<>("DEL_FAIL", HttpStatus.BAD_REQUEST); // 400
        }
    }

    // 댓글을 등록하는 메서드
    @ResponseBody
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDTO comment, HttpSession session, Model m) {
        String writer = (String) session.getAttribute("id");

        if (writer==null || writer.equals("")) {  // 세션이 풀렸을경우
            m.addAttribute("msg", "SESSION_ERR");
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/") // 메인페이지로 리다이렉트
                    .build();
        }

        // 댓글 작성자 세팅
        comment.setWriter(writer);
        ProjectFileVO file = FDAO.selectFileImage(writer);
        logger.info("file : "+file);

        // 댓글의 파일경로를 지정

        if (file != null) {
            // azure 스토리지는 앞에 날짜 설정이 불필요..
//            String fileName = file.getSave_dir()+"/"+file.getUuid()+"_th_"+file.getFile_name(); // 댓글의 이미지 경로
            String fileName = file.getUuid() + "_" + file.getFile_name();
            comment.setImage_file(fileName);
        } else {
            comment.setImage_file(null);
        }

        try {
            if (SC.register(comment) > 0) {
                logger.info("댓글 등록 성공!");
            } else {
                throw new Exception();
            }
            return new ResponseEntity<String>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("댓글을 등록하지 못하였습니다. : {}", e.getMessage(), e);
            return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

}
