package com.youngmok.myboard.controller;

import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.dao.UserDAO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.UserVO;
import com.youngmok.myboard.handler.AzureFileHandler;
import com.youngmok.myboard.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final UserService US;

    private final FileDAO FDAO;

    private final UserDAO UDAO;

    @Autowired // 생성자를 통하여 필드주입
    public RegisterController(UserService userService, FileDAO FDAO, UserDAO UDAO) {
        this.US = userService;
        this.FDAO = FDAO;
        this.UDAO = UDAO;
    }

    // azure 파일관리
    private static ProjectFileVO userFile(UserVO user, BindingResult result, MultipartFile file) {
        ProjectFileVO imgfile = null;
        if (file != null && !file.isEmpty()) {
            // Azure Blob Storage를 사용하도록 수정된 FileHandler 객체 생성
            AzureFileHandler azurefileHandler = new AzureFileHandler();
            // 파일 업로드
            imgfile = azurefileHandler.uploadFiles(new MultipartFile[]{file});
        }
        return imgfile;
    }

    // 회원가입시 받는 날짜를 DB형식에 맞게 바꿔줌
    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
    }

    // 회원가입시 중복된 회원이 있는지 검사
    @ResponseBody
    @PostMapping("/idChk")
    public int idChk(@RequestBody UserVO user) {
        logger.info("아이디임 : "+user.getId());
        return  US.idChk(user.getId());
    }

    // 회원가입 페이지로 가는 메서드
    @GetMapping("/add")
    public String register(Model m) {
        m.addAttribute("mode", "new");
        return "login&register/registerForm";
    }

    // 회원가입 페이지에서 정보를 받아 처리하는 메서드
    @PostMapping("/join")
    public String join(@Valid UserVO user, BindingResult result, Model m,
                       @RequestParam(name = "file", required = false) MultipartFile file, RedirectAttributes rattr) throws Exception {
        ProjectFileVO imgFile = userFile(user, result, file);

        // 데이터 검증이 실패했다면
        if (result.hasErrors()) {
            logger.warn("데이터 검증실패 에러남");
            return "login&register/registerForm";
        }

        try {
            boolean isOk = US.join(user, imgFile);
            if (isOk) {
               logger.info("회원가입을 성공하였습니다.");
            } else {
                throw new Exception();
            }
            rattr.addFlashAttribute("msg", "USER_JOIN_OK");
        } catch (Exception e) {
            rattr.addFlashAttribute("msg", "USER_JOIN_FAIL");
            logger.error("회원가입이 실패 하였습니다. : {}", e.getMessage(), e);
        }
        // DB에 신규회원 정보를 저장하고 홈으로 이동
        return "redirect:/";
    }

    // 회원정보 수정 페이지로 가는 메서드
    @GetMapping("/uesrModify")
    public String uesrModify(HttpSession session, Model m,RedirectAttributes rattr) {
        String id = (String) session.getAttribute("id");

        try {
            UserVO user = UDAO.selectUser(id);  // 해당 유저를 가져옴
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String userBirth = sdf.format(user.getBirth());
            // 파일 수정을 위해서 UUID에서 해당 유저의 파일정보를 가져옴
            ProjectFileVO imgFile = FDAO.selectFileImage(id);
//        String fileName = "/fileUpload/"+file.getSave_dir()+"/"+file.getUuid()+"_"+file.getFile_name();

            m.addAttribute("file", imgFile);
            m.addAttribute("userBirth", userBirth);
            m.addAttribute("user", user);
        } catch (Exception e) {
            logger.error("회원정보를 불러오는데 실패 하였습니다. : {}", e.getMessage(), e);
            rattr.addFlashAttribute("msg", "USER_INFO_FAIL");
        }

        return "login&register/registerForm";
    }

    //회원가입,수정시 파일처리 공통메서드
    // 로컬 파일관리
//    private static List<ProjectFileVO> userFile(UserVO user, BindingResult result, MultipartFile file) {
//
//        List<ProjectFileVO> fileList = new ArrayList<>();
//        if (file != null && !file.isEmpty()) {  // 파일이 있다면
//            FileHandler fileHandler = new FileHandler();
//            fileList = fileHandler.uploadFiles(new MultipartFile[]{file});
//        }
//
//        return fileList;
//    }

    // 수정된 회원정보를 처리하는 메서드
    @PostMapping("/userModify")
    public String userModify(@Valid UserVO user, BindingResult result, Model m,
                             @RequestParam(name = "file", required = false) MultipartFile file, HttpSession session, RedirectAttributes rattr) {
        String id = (String) session.getAttribute("id");
        user.setId(id);
        logger.info("id : "+ id);

        ProjectFileVO imgFile = userFile(user, result, file); // 따로 추출한 메서드의 결과값이 파일리스트로 들어감
        boolean isOk;

        try {
            isOk = US.modify(user, imgFile);
            if (isOk) {
                logger.info("회원정보수정을 성공하였습니다.");
            } else {
                throw new Exception();
            }
            rattr.addFlashAttribute("msg", "USER_MOD_OK");
            session.invalidate(); // 회원정보를 수정하면 세션해제후 다시 로그인
        } catch (Exception e) {
            logger.error("회원정보 수정하는것을 실패 하였습니다. : {}", e.getMessage(), e);
            rattr.addFlashAttribute("msg", "USER_MOD_ERR");
        }
        // 데이터 검증
        if (result.hasErrors()) {
            logger.warn("데이터 검증에 실패하였습니다.");
            return "redirect:/register/uesrModify";
        }
        return "redirect:/";
    }

    // 회원탈퇴 메서드
    @GetMapping("/unregister")
    public String userUnregister(HttpSession session, RedirectAttributes rattr) {
        String id = (String) session.getAttribute("id");
        logger.info("id : "+id);
        try {
            int isOk = US.userUnregister(id);

            if (isOk > 0) {
                logger.info("회원탈퇴 성공!");
            } else {
                throw new Exception();
            }
            rattr.addFlashAttribute("msg", "USER_DEL_OK");
        } catch (Exception e) {
            logger.error("회원탈퇴에 실패 하였습니다. : {}", e.getMessage(), e);
            rattr.addFlashAttribute("msg", "USER_DEL_ERR");
        }
        session.invalidate();  // 탈퇴 후 세션 정지
        return "redirect:/";
    }


}
