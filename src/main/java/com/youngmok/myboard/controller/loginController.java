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

        session.invalidate();

        return "redirect:/";
    }
    
    
    // 비밀번호 찾기에서 개인정보 입력시 해당 회원이 있는지 검사
    @ResponseBody
    @PostMapping("/pwd")
    public ResponseEntity<Map<String, Object>> pwdSearch(@RequestBody UserVO user) {

        System.out.println(user);

        HashMap<String, Object> response = new HashMap<>(); // 회원 아이디를 보내기 위해 맵 생성

        try {
            UserVO user1 = US.pwdSearch(user); // 입력된 계정정보에 해당하는 회원을 가져옴
            response.put("id", user1.getId()); // 회원 아이디를 맵에 저장
            response.put("status", "OK");       // 상태코드도 같이...

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "ERR");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    // 비밀번호 변경 메서드
    @ResponseBody
    @PostMapping("/change")
    public ResponseEntity<String> pwdChange(@RequestBody UserVO user) {

        System.out.println(user);

        try {
            int isOK = US.pwdChange(user);  // 해당하는 유저의 비밀번호를 변경
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

        if(!loginCheck(id, pwd)) {
            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");

            return "redirect:/login/login?msg="+msg;
        }

        UserVO user = US.getUser(id);

        HttpSession session = request.getSession();  // 세션 생성
        session.setAttribute("user", user);

        if(rememberId) {

            Cookie cookie = new Cookie("user", id);
            response.addCookie(cookie);
        } else {
 
            Cookie cookie = new Cookie("user", id);
            cookie.setMaxAge(0);

            response.addCookie(cookie);
        }

        toURL = (toURL==null || toURL.equals("")) ? "/" : toURL;  // 로그인 페이지에서 바로 어디론가 보낼때 사용

        return "redirect:"+toURL;
    }

    // 로그인 유효성 검사
    private boolean loginCheck(String id, String pwd) {
        UserVO user = null;

        try {
            user = US.login(id,pwd);  // 서비스단에서 유효성 검사 실행
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return user!=null;  // 널이 아니라면 유효성 검사를 통과 했다는것
    }
}
