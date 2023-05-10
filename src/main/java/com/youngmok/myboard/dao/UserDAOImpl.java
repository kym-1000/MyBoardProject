package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SqlSession session;
    private static final String namespace = "com.youngmok.myboard.dao.UserMapper.";

    @Override
    public int insertUser(UserVO user) {
        System.out.println("\"여기까지옴\" = " + " dao 여기까지옴");
        return session.insert(namespace+"insert",user);
    }

    @Override
    public UserVO selectUser(String id) {
        return session.selectOne(namespace+"selectLoginUser",id);
    }

    @Override
    public int selectId(String id) {
        return session.selectOne(namespace+"idCheck",id);
    }

    @Override
    public void deleteAll() {
        session.delete(namespace + "deleteAll");
    }

    @Override
    public int userCount() {
        return session.selectOne(namespace+"userCount");
    }

    @Override
    public int deleteUser(String id) {
        return session.delete(namespace+"deleteUser",id);
    }

    @Override
    public int modifyUser(UserVO user) {
        return session.update(namespace+"modifyUser",user);
    }

    @Override
    public int searchUser(UserVO user) {
        return session.selectOne(namespace+"searchUser",user);
    }

    @Override
    public int modifyUserPwd(UserVO user) {
        return session.update(namespace+"modifyUserPwd",user);
    }
}
