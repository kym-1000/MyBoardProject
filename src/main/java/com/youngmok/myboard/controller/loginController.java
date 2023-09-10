package com.youngmok.myboard.controller;

import com.youngmok.myboard.domain.UserVO;
import com.youngmok.myboard.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private static final Logger logger = LoggerFactory.getLogger(loginController.class);


    private final UserService US;

    @Autowired // 생성자를 통하여 필드주입
    public loginController(UserService userService){
        this.US = userService;
    }

    // 로그인 페이지로 이동하는 메서드
    @GetMapping("/login")
    public String loginform() {
        return "login&register/login";
    }

    // 로그아웃 메서드
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes rattr) {

        String id = (String) session.getAttribute("id");
        session.invalidate();

        rattr.addFlashAttribute("logout", id); // 채팅에 로그아웃한 아이디를 알리기 위하여..
        return "redirect:/";
    }


    // 비밀번호 찾기에서 개인정보 입력시 해당 회원이 있는지 검사하는 메서드
    @ResponseBody
    @PostMapping("/pwd")
    public ResponseEntity<Map<String, Object>> pwdSearch(@RequestBody UserVO user) {
        logger.info("user : "+user);
        HashMap<String, Object> response = new HashMap<>(); // 회원 아이디를 보내기 위해 맵 생성
        try {
            UserVO user1 = US.pwdSearch(user); // 입력된 계정정보에 해당하는 회원을 가져옴
            response.put("id", user1.getId()); // 회원 아이디를 맵에 저장
            response.put("status", "OK");       // 상태코드도 같이...
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("회원정보을 불러오지 못하였습니다. : {}", e.getMessage(), e);
            response.put("status", "ERR");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 비밀번호 찾기후  변경 메서드
    @ResponseBody
    @PostMapping("/change")
    public ResponseEntity<String> pwdChange(@RequestBody UserVO user) {
        logger.info("user : "+user);
        try {
            int isOK = US.pwdChange(user);  // 해당하는 유저의 비밀번호를 변경
            if (isOK > 0) {
                logger.info("비밀번호 변경 성공");
            } else {
                throw new Exception();
            }
            return new ResponseEntity<String>("OK", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("비밀번호 변경을 실패하였습니다. : {}", e.getMessage(), e);
            return new ResponseEntity<String>("ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 로그인 메서드
    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberId,
                        HttpServletRequest request, HttpServletResponse response,RedirectAttributes rattr) throws Exception {
        logger.info("id : "+id);

        if (!loginCheck(id, pwd)) {
            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");

            return "redirect:/login/login?msg=" + msg;
        }

        try {
            UserVO user = US.getUser(id);
            logger.info("user : "+user);
            HttpSession session = request.getSession();  // 세션 생성
            session.setAttribute("profile", user.getProfile());  // 프로필파일 세션
            session.setAttribute("id", user.getId());           // id 세션
            session.setAttribute("authority", user.getAuthority()); // 유저권한 세션

            if (rememberId) { // 쿠키에 아이디저장 되어있을 경우
                Cookie cookie = new Cookie("id", user.getId());
                response.addCookie(cookie);
            } else { // 아닐경우 쿠키삭제
                Cookie cookie = new Cookie("id", user.getId());
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        } catch (Exception e) {
            logger.error("로그인을 실패하였습니다. : {}", e.getMessage(), e);
            rattr.addFlashAttribute("msg","USER_LOGIN_FAIL");
            return "redirect:/";
        }

        toURL = (toURL == null || toURL.equals("")) ? "/" : toURL;  // 로그인 페이지에서 바로 어디론가 보낼때 사용

        return "redirect:" + toURL;
    }

    // 로그인 유효성 검사
    private boolean loginCheck(String id, String pwd) {
        UserVO user = null;

        try {
           logger.debug("로그인 유효성 체크 직전");
            user = US.login(id, pwd);  // 서비스단에서 유효성 검사 실행
        } catch (Exception e) {
            logger.error("로그인 유효성 검사를 실패하였습니다. : {}", e.getMessage(), e);
            return false;
        }

        return user != null;  // 널이 아니라면 유효성 검사를 통과 했다는것
    }
}
