package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private final SqlSession session;

    @Autowired
    public UserDAOImpl(SqlSession session) {
        this.session = session;
    }
    private static final String namespace = "com.youngmok.myboard.dao.UserMapper.";

    @Override
    public int insertUser(UserVO user) {
        return session.insert(namespace+"insert",user);
    }

    @Override
    public UserVO selectUser(String id) {
        logger.info("id : " + id);
        return session.selectOne(namespace+"selectLoginUser",id);
    }

    @Override
    public int selectId(String id) {
        logger.info("id : " + id);
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
    public UserVO searchUser(UserVO user) {
        return session.selectOne(namespace+"searchUser",user);
    }

    @Override
    public int modifyUserPwd(UserVO user) {
        return session.update(namespace+"modifyUserPwd",user);
    }

    @Override
    public List<UserVO> selectUserList() {
        return session.selectList(namespace+"selectUserList");
    }
}
