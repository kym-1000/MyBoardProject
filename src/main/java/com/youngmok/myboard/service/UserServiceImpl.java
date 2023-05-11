package com.youngmok.myboard.service;

import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.dao.UserDAO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.UserVO;
//import com.youngmok.myboard.handler.AzureFileHandler;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO UDAO;

    @Autowired
    private FileDAO FDAO;

//    @Autowired
//    private AzureFileHandler FH;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean join(UserVO user, List<ProjectFileVO> fileList) {

        // 아이디가 중복되면 회원가입 실패
        // 아이디와 일치하는 정보를 db에서 가져옴
        UserVO tmpUser = UDAO.selectUser(user.getId());
        // 유저가 널이 아니라면 이미 가입된 회원 => 아이디 중복 => 회원가입 실패
        if(tmpUser != null) {
            return false;
        }

        // 아이디가 중복되지 않았으면 회원가입
        // 아이디 규칙(유효성 검사) 체크가 맞지않으면 실패
        // 아이디가 유효성 검사 : 아이디가 입력되었는지만 체크
        if(user.getId() == null || user.getId().length() == 0) {
            return false;
        }

        //비밀번호 유효성 검사 : 비밀번호가 입력되었는지만 체크
        if(user.getPwd() == null || user.getPwd().length() == 0) {
            return false;
        }

        // 회원가입
        // 비밀번호를 암호화 과정
        String pw = user.getPwd();
        // encode (암호화) / matches(원래비번,암호화된 비번)
        String encodePw = passwordEncoder.encode(pw); // pw 암호화
        //회원 비밀번호를 암호화된 비밀번호로 수정
        user.setPwd(encodePw);
        //회원가입 => insert

        System.out.println("user = " + user);
        int isOk = UDAO.insertUser(user);
        
        // 파일정보를 저장
        for (ProjectFileVO file : fileList) {
            file.setUser(user.getId()); // 파일 정보와 사용자 정보를 연결하기 위해 bno 설정
            FDAO.insertFile(file);
        }

        System.out.println("fileList = " + fileList);

        return (isOk > 0) ? true : false;
    }

    @Override
    public int userUnregister(String id) {
        return UDAO.deleteUser(id);
    }

    @Override
    public UserVO login(String id, String pwd) {
        UserVO user = UDAO.selectUser(id);
        System.out.println(user);
        if (user == null) {
            return null;
        }

        System.out.println(pwd);
        System.out.println(user.getPwd());
        System.out.println(passwordEncoder.matches(pwd, user.getPwd()));

        if (passwordEncoder.matches(pwd, user.getPwd())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public boolean modify(UserVO user, List<ProjectFileVO> fileList) {
        
        if(user.getPwd() == null || user.getPwd().length() == 0) {
            return false;
        }

        // 회원수정
        // 비밀번호를 암호화 과정
        String pw = user.getPwd();
        // encode (암호화) / matches(원래비번,암호화된 비번)
        String encodePw = passwordEncoder.encode(pw); // pw 암호화
        //회원 비밀번호를 암호화된 비밀번호로 수정
        user.setPwd(encodePw);
        //회원 수정

        if(FDAO.selectFileImage(user.getId())==null){ // 기존에 프로필이 존재하지 않다면...
            for (ProjectFileVO file : fileList) {
                file.setUser(user.getId()); // 파일 정보와 사용자 정보를 연결하기 위해 bno 설정
                FDAO.insertFile(file);
            }
        } else {
            // 수정된 파일 정보를 저장
            for (ProjectFileVO file : fileList) {
                file.setUser(user.getId()); // 파일 정보와 사용자 정보를 연결하기 위해 bno 설정
                FDAO.profileFileModify(file);
            }
        }

        int isOk = UDAO.modifyUser(user);

        System.out.println("fileList = " + fileList);

        return (isOk > 0) ? true : false;
    }

    @Override
    public UserVO pwdSearch(UserVO user) {
        return UDAO.searchUser(user);
    }

    @Override
    public int pwdChange(UserVO user) {

        if(user.getPwd() == null || user.getPwd().length() == 0) {
            return 0;
        }

        // 회원수정
        // 비밀번호를 암호화 과정
        String pw = user.getPwd();
        // encode (암호화) / matches(원래비번,암호화된 비번)
        String encodePw = passwordEncoder.encode(pw); // pw 암호화
        //회원 비밀번호를 암호화된 비밀번호로 수정
        user.setPwd(encodePw);
        //회원 수정

        return UDAO.modifyUserPwd(user);
    }

    @Override
    public int idChk(String id) {
        return UDAO.selectId(id);
    }

}
