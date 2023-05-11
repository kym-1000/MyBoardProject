package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.UserVO;

public interface UserDAO {
    int insertUser(UserVO user);

    UserVO selectUser(String id);

    int selectId(String id);

    void deleteAll();

    int userCount();

    int deleteUser(String id);

    int modifyUser(UserVO user);

    UserVO searchUser(UserVO user);

    int modifyUserPwd(UserVO user);
}
