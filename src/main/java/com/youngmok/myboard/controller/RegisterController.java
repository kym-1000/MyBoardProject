package com.youngmok.myboard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.dao.UserDAO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.UserVO;
import com.youngmok.myboard.handler.AzureFileHandler;
//import com.youngmok.myboard.handler.FileHandler;
import com.youngmok.myboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService US;

    @Autowired
    private FileDAO FDAO;

    @Autowired
    private UserDAO UDAO;

    // 회원가입시 받는 날짜를 DB형식에 맞게 바꿔줌
    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
    }

    // 회원가입시 중복된 회원이 있는지 검사
    @ResponseBody
    @PostMapping("/idChk")
    public int idChk(String id) {
        System.out.println("id = " + id);
        return US.idChk(id);
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
                       @RequestParam(name="file",required = false) MultipartFile file,RedirectAttributes rattr) throws Exception {
        List<ProjectFileVO> fileList  = userFile(user, result, file);
        boolean isOk;

        try {
            isOk = US.join(user,fileList);
            if (isOk) {
                System.out.println("\"회원가입여부\" = " + "회원가입성공!");
            } else {
                System.out.println("\"회원가입여부\" = " + "회원가입실패!");
            }
            rattr.addFlashAttribute("msg", "USER_join_OK");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result.hasErrors()) {
            System.out.println("\"hasErrors\" = " + "에러남");
            return "login&register/registerForm";
        }

        // DB에 신규회원 정보를 저장하고 홈으로 이동
        return "redirect:/";
    }

    
    // 회원정보 수정 페이지로 가는 메서드
    @GetMapping("/uesrModify")
    public String uesrModify(HttpSession session,Model m){
        String id = (String) session.getAttribute("id");

        UserVO user = UDAO.selectUser(id);  // 해당 유저를 가져옴

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String userBirth = sdf.format(user.getBirth());
        // 파일 수정을 위해서 UUID에서 해당 유저의 파일정보를 가져옴
        ProjectFileVO file = FDAO.selectFileImage(id);
//        String fileName = "/fileUpload/"+file.getSave_dir()+"/"+file.getUuid()+"_"+file.getFile_name();

        m.addAttribute("file", file);
        m.addAttribute("userBirth", userBirth);
        m.addAttribute("user", user);

        return "login&register/registerForm";
    }

    // 수정된 회원정보를 처리하는 메서드
    @PostMapping("/userModify")
    public String userModify(@Valid UserVO user,BindingResult result, Model m,
                             @RequestParam(name="file",required = false) MultipartFile file,HttpSession session,RedirectAttributes rattr){
        String id = (String)session.getAttribute("id");
        user.setId(id);

        List<ProjectFileVO> fileList  = userFile(user, result, file);
        boolean isOk;

        try {
            isOk = US.modify(user,fileList);
            if (isOk) {
                System.out.println("\"회원정보수정여부\" = " + "회원수정성공!");
            } else {
                System.out.println("\"회원정보수정여부\" = " + "회원수정실패!");
            }
            rattr.addFlashAttribute("msg", "USER_MOD_OK");
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "USER_MOD_ERR");
        }

        if (result.hasErrors()) {
            System.out.println("\"hasErrors\" = " + "에러남");
            return "redirect:/register/uesrModify";
        }
        return "redirect:/";
    }

    //회원가입,수정시 파일처리 공통메서드
    // 로컬 파일관리
//    private static List<ProjectFileVO> userFile(UserVO user, BindingResult result, MultipartFile file) {
//        System.out.println("result=" + result);
//        System.out.println("user=" + user);
//
//        List<ProjectFileVO> fileList = new ArrayList<>();
//        if (file != null && !file.isEmpty()) {  // 파일이 있다면
//            FileHandler fileHandler = new FileHandler();
//            fileList = fileHandler.uploadFiles(new MultipartFile[]{file});
//        }
//
//        return fileList;
//    }

    // azure 파일관리
    private static List<ProjectFileVO> userFile(UserVO user, BindingResult result, MultipartFile file) {
        System.out.println("result=" + result);
        System.out.println("user=" + user);

        List<ProjectFileVO> fileList = new ArrayList<>();
        if (file != null && !file.isEmpty()) {
            // Azure Blob Storage를 사용하도록 수정된 FileHandler 객체 생성
            AzureFileHandler fileHandler = new AzureFileHandler();

            // 파일 업로드
            fileList = fileHandler.uploadFiles(new MultipartFile[]{file});
        }

        return fileList;
    }

    // 회원탈퇴 메서드
    @GetMapping("/unregister")
    public String userUnregister(HttpSession session, RedirectAttributes rattr) {
        String id = (String) session.getAttribute("id");

        try {
            int isOk = US.userUnregister(id);

            if(isOk>0){
                System.out.println("\"회원탈퇴 성공!\" = " + "회원탈퇴 성공!");
            } else{
                System.out.println("\"회원탈퇴 실패!\" = " + "회원탈퇴 실패!");
            }
            rattr.addFlashAttribute("msg", "USER_DEL_OK");
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "USER_DEL_ERR");
        }

        session.invalidate();  // 탈퇴 후 세션 정지

        return "redirect:/";
    }


}
