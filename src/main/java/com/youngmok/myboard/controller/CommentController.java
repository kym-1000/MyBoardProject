package com.youngmok.myboard.controller;


import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.domain.CommentDTO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService SC;

    @Autowired
    FileDAO FDAO;

    // 지정된 게시물의 모든 댓글을 가져오는 메서드
    @ResponseBody
    @GetMapping("/comments") // comments?bno=3333
    public ResponseEntity<List<CommentDTO>> list(Integer bno){
        System.out.println("bno = " + bno);
        List<CommentDTO> list = null;
        try {
            list = SC.getList(bno); // 댓글 리스트를 가져옴
            System.out.println("list = " + list);
            return new ResponseEntity<List<CommentDTO>>(list, HttpStatus.OK); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDTO>>(HttpStatus.BAD_REQUEST); // 400
        }

    }

//     댓글을 삭제하는 메서드
    @DeleteMapping("/comments/{cno}")  //  /comments/1?bno=1085  #{}로 가져올떄는 PathVariable
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno) {
        System.out.println("cno = " + cno);
        System.out.println("bno = " + bno);

        try {
            int rowCnt = SC.remove(cno,bno);  // 해당하는 댓글을 삭제함

            if(rowCnt != 1){
                throw new Exception("Delete Failed");
            }

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_FAIL", HttpStatus.OK);
        }
    }

    // 댓글을 등록하는 메서드
    @ResponseBody
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDTO comment, HttpSession session, Model m) {
        String writer = (String) session.getAttribute("id");

        if(writer.equals("")){

            m.addAttribute("msg", "SESSION_ERR");
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/")
                    .build();
        }

        // 댓글 작성자 세팅
        comment.setWriter(writer);
        ProjectFileVO file = FDAO.selectFileImage(writer);

        // 댓글의 파일경로를 지정

        if(file != null){
            // azure 스토리지는 앞에 날짜 파일이 불필요..
//            String fileName = file.getSave_dir()+"/"+file.getUuid()+"_th_"+file.getFile_name(); // 댓글의 이미지 경로

            String fileName = file.getUuid()+"_"+file.getFile_name();


            comment.setImage_file(fileName);
        } else {
            comment.setImage_file(null);
        }


        try {
            if (SC.register(comment)!=1){
                throw new Exception("Write failed");
            }
            return new ResponseEntity<String>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("등록실패!");
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }


}
