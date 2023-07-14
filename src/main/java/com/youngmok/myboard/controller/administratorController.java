package com.youngmok.myboard.controller;


import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.DeleteListDTO;
import com.youngmok.myboard.domain.UserVO;
import com.youngmok.myboard.service.BoardService;
import com.youngmok.myboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("ad")
public class administratorController {

    @Autowired
    UserService US;

    @Autowired
    BoardService BS;

    @GetMapping("/userList")
    public String userList(Model m){

        List<UserVO> userList = US.getUserList();
        
        if(userList != null){
            System.out.println(" = " + "userList 불러오기 성공");
        } else {
            System.out.println(" = " + "userList 불러오기 실패");
        }

        m.addAttribute("userList",userList);

        return "administrator";
    }

    @DeleteMapping("/userDelete/{id}")
    public ResponseEntity<String> userDelete(@PathVariable String id){
        try {
            int isOk = US.userUnregister(id);

            if(isOk != 1){
                throw new Exception("Delete Failed");
            }

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_FAIL", HttpStatus.OK);
        }
    }

    @PostMapping("/boardDelete")
    public ResponseEntity<String> boardDelete(@RequestBody DeleteListDTO DeleteListDTO){

        ArrayList<Integer> deleteList = DeleteListDTO.getDeleteList();

        try {
            int isOk = BS.adBoardListDelete(deleteList);

            if(isOk <= 0){
                throw new Exception("Delete Failed");
            }

            return ResponseEntity.ok("board delete success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("board delete fail");
        }

    }



}
