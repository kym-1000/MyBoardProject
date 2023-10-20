package com.youngmok.myboard.controller;


import com.youngmok.myboard.domain.DeleteListDTO;
import com.youngmok.myboard.domain.UserVO;
import com.youngmok.myboard.service.BoardService;
import com.youngmok.myboard.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("ad")
public class administratorController {

    private static final Logger logger = LoggerFactory.getLogger(administratorController.class);

    private final UserService US;

    private final BoardService BS;

    @Autowired  // 생성자를 통하여 필드주입
    public administratorController(UserService userService, BoardService boardService) {
        this.US = userService;
        this.BS = boardService;
    }

    // 회원목록을 불러오는 메서드
    @GetMapping("/userList")
    public String userList(Model m) {
        try {
            List<UserVO> userList = US.getUserList();
            logger.info("userList : "+userList);
            m.addAttribute("userList", userList);
        } catch (Exception e) {
            logger.error("회원목록을 불러오지 못하였습니다. : {}", e.getMessage(), e);
            m.addAttribute("msg", "USER_INFO_FAIL");
        }
        return "administrator";
    }

    // 세션에서 아이디 && 권한 체크
    private boolean checkUserSession(HttpSession session) {
        String sessionId = (String) session.getAttribute("id");
        int authority = (int) session.getAttribute("authority");
        return sessionId != null && authority != 1;
    }

    // 회원을 삭제하는 메서드
    @DeleteMapping("/userDelete/{id}")
    public ResponseEntity<String> userDelete(@PathVariable String id, HttpSession session) {
        try {
            if (!checkUserSession(session)) {
                throw new Exception("session Failed");
            }
            int isOk = US.userUnregister(id); // 관리자 회원 삭제
            if (isOk != 1) {
                throw new Exception("Delete Failed");
            }
            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("회원삭제가 실패하였습니다. : {}", e.getMessage(), e);
            return new ResponseEntity<>("DEL_FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글들을 삭제하는 메서드
    @PostMapping("/boardDelete")
    public ResponseEntity<String> boardDelete(@RequestBody DeleteListDTO DeleteListDTO, HttpSession session) {
        ArrayList<Integer> deleteList = DeleteListDTO.getDeleteList();

        try {
            if (!checkUserSession(session)) {
                throw new Exception("session Failed");
            }
            int isOk = BS.adBoardListDelete(deleteList); // 게시물(들) 삭제
            if (isOk <= 0) {
                throw new Exception("Delete Failed");
            }
            return ResponseEntity.ok("board delete success");
        } catch (Exception e) {
            logger.error("게시물삭제를 실패하였습니다. : {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("board delete fail");
        }
    }

}
