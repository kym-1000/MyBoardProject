package com.youngmok.myboard.controller;

import com.youngmok.myboard.dao.UserDAO;
import com.youngmok.myboard.domain.UserVO;
import com.youngmok.myboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class loginController {

    @Autowired
    UserService US;

    @GetMapping("/login")
    public String loginform(){
        return "login&register/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 1. 세션을 종료
        session.invalidate();
        // 2. 홈으로 이동
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/pwd")
    public ResponseEntity<Map<String, Object>> pwdSearch(@RequestBody UserVO user) {

        System.out.println(user);

        HashMap<String, Object> response = new HashMap<>();

        try {
            UserVO user1 = US.pwdSearch(user);
            response.put("id", user1.getId());
            response.put("status", "OK");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "ERR");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @ResponseBody
    @PostMapping("/change")
    public ResponseEntity<String> pwdChange(@RequestBody UserVO user) {

        System.out.println(user);

        try {
            int isOK = US.pwdChange(user);
            if(isOK > 0){
                System.out.println("비밀번호 변경 성공!");
            } else{
                System.out.println("비밀번호 변경 실패!");
            }
            return new ResponseEntity<String>("OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("ERR", HttpStatus.BAD_REQUEST);
        }

    }

    // 로그인 메서드
    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberId,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. id와 pwd를 확인
        if(!loginCheck(id, pwd)) {
            // 2-1   일치하지 않으면, loginForm으로 이동
            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");

            return "redirect:/login/login?msg="+msg;
        }

        // 2-2. id와 pwd가 일치하면,
        //  세션 객체를 얻어오기
        HttpSession session = request.getSession();
        //  세션 객체에 id를 저장
        session.setAttribute("id", id);

        if(rememberId) {
            //     1. 쿠키를 생성
            Cookie cookie = new Cookie("id", id); // ctrl+shift+o 자동 import
//		       2. 응답에 저장
            response.addCookie(cookie);
        } else {
            // 1. 쿠키를 삭제
            Cookie cookie = new Cookie("id", id); // ctrl+shift+o 자동 import
            cookie.setMaxAge(0); // 쿠키를 삭제
//		       2. 응답에 저장
            response.addCookie(cookie);
        }
//		       3. 홈으로 이동
        toURL = (toURL==null || toURL.equals("")) ? "/" : toURL;

        return "redirect:"+toURL;
    }

    // 로그인 유효성 검사
    private boolean loginCheck(String id, String pwd) {
        UserVO user = null;

        try {
            user = US.login(id,pwd);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return user!=null;
    }
}
