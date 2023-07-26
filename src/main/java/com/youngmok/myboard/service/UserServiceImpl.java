package com.youngmok.myboard.service;

import com.youngmok.myboard.dao.CommentDAO;
import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.dao.UserDAO;
import com.youngmok.myboard.domain.FileList;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO UDAO;

    @Autowired
    private FileDAO FDAO;

//    @Autowired
//    private AzureFileHandler FH;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CommentDAO CDAO;

    // 회원가입 메서드
    @Override
    public boolean join(UserVO user, List<ProjectFileVO> fileList) {

        // 아이디가 중복되면 회원가입 실패
        // 아이디와 일치하는 정보를 db에서 가져옴
        UserVO tmpUser = UDAO.selectUser(user.getId());
        // 유저가 널이 아니라면 이미 가입된 회원 => 아이디 중복 => 회원가입 실패
        if (tmpUser != null) {
            logger.warn("이미 가입되어있는 id!");
            return false;
        }

        // 아이디가 중복되지 않았으면 회원가입
        // 아이디 규칙(유효성 검사) 체크가 맞지않으면 실패
        // 아이디가 유효성 검사 : 아이디가 입력되었는지만 체크
        if (user.getId() == null || user.getId().length() == 0) {
            logger.warn("아이디값 유효성 검사 실패!");
            return false;
        }

        //비밀번호 유효성 검사 : 비밀번호가 입력되었는지만 체크
        if (user.getPwd() == null || user.getPwd().length() == 0) {
            logger.warn("비밀번호값 유효성 검사 실패!");
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

        if (fileList != null && !fileList.isEmpty()) {  // 등록된 프로필 사진이 있다면
            FileList file = getFileList(fileList, user.getId());
            user.setProfile(file.getProfile());
            FDAO.insertFile(file.getFile());
        }

        int isOk = UDAO.insertUser(user);
        // uuid+파일이름만 세팅!
        return (isOk > 0) ? true : false;
    }

    // 유저 탈퇴 메서드
    @Override
    public int userUnregister(String id) {

        if (FDAO.selectFileImage(id) != null) { // 프로필사진이 존재한다면
            FDAO.deleteUserFile(id);
        }

        return UDAO.deleteUser(id); // 유저 삭제
    }

    // 로그인 메서드
    @Override
    public UserVO login(String id, String pwd) {
        UserVO user = UDAO.selectUser(id);
        if (user == null) {
            return null;
        }

        if (passwordEncoder.matches(pwd, user.getPwd())) {
            return user;
        } else {
            return null;
        }
    }

    // 프로필 파일 처리 메서드
    public FileList getFileList(List<ProjectFileVO> fileList, String id) {

        FileList fdto = new FileList();  // 1개의 객체와 1개의 스트링 값을 묶어서 전달하기 위함
        ProjectFileVO file = fileList.get(0);

        fdto.setFile(file);
        fdto.setProfile(file.getUuid() + "_" + file.getFile_name());
        fdto.getFile().setUser(id);
        fdto.getFile().setFile_type(1);

        return fdto;
    }

    @Override
    public boolean modify(UserVO user, List<ProjectFileVO> fileList) {

        if (user.getPwd() == null || user.getPwd().length() == 0) { // 기초적인 유효성 검사
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

        int isOk;

        if (fileList.size() == 0) {  // 프로필 파일 수정이 일어나지 않았다면
            isOk = UDAO.modifyUser(user);
        } else if (FDAO.selectFileImage(user.getId()) == null) { // 기존에 프로필사진이 존재하지 않다면...
            FileList file = getFileList(fileList, user.getId());
            FDAO.insertFile(file.getFile());                  // 새로운 파일 저장
            CDAO.updateProfile(file.getProfile(), user.getId());  // 댓글 프로필 수정
            user.setProfile(file.getProfile());
            isOk = UDAO.modifyUser(user);
        } else {
            // 수정된 파일 정보를 저장
            FileList file = getFileList(fileList, user.getId());
            FDAO.profileFileModify(file.getFile());           // 파일 데이터베이스 수정
            CDAO.updateProfile(file.getProfile(), user.getId());  // 댓글 프로필 수정
            user.setProfile(file.getProfile());
            isOk = UDAO.modifyUser(user);
        }
        return (isOk > 0) ? true : false;
    }

    @Override
    public UserVO pwdSearch(UserVO user) {
        return UDAO.searchUser(user);
    }

    @Override
    public int pwdChange(UserVO user) {

        if (user.getPwd() == null || user.getPwd().length() == 0) { // 기초적인 유효성 검사
            logger.warn("비밀번호변경값 유효성 검사 실패!");
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
    public UserVO getUser(String id) {
        return UDAO.selectUser(id);
    }

    @Override
    public List<UserVO> getUserList() {
        return UDAO.selectUserList();
    }

    @Override
    public int idChk(String id) {
        return UDAO.selectId(id);
    }

}
