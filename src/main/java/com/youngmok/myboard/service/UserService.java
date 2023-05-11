package com.youngmok.myboard.service;

import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.UserVO;

import java.util.List;

public interface UserService {



    int idChk(String id);

    boolean join(UserVO user, List<ProjectFileVO> fileList);

    int userUnregister(String id);

    UserVO login(String id, String pwd);

    boolean modify(UserVO user, List<ProjectFileVO> fileList);

    UserVO pwdSearch(UserVO user);

    int pwdChange(UserVO user);
}
