package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.CommentDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO{

    private final SqlSession session;

    @Autowired
    public CommentDAOImpl(SqlSession session) {
        this.session = session;
    }

    private static final String namespace = "com.youngmok.myboard.dao.CommentMapper.";

    @Override
    public int commentInsert(CommentDTO comment) {
        return session.insert(namespace+"commentInsert",comment);
    }

    @Override
    public List<CommentDTO> commentSelectList(Integer bno) {
        return session.selectList(namespace+"commentSelectList",bno);
    }

    @Override
    public int deleteComment(Integer cno, Integer bno) {
        HashMap map = new HashMap();
        map.put("cno", cno);
        map.put("bno", bno);
        return session.delete(namespace + "deleteComment", map);
    }

    @Override
    public void deleteAll() {
        session.delete(namespace + "deleteAll");
    }

    @Override
    public int commentCount(Integer bno) {
        return session.selectOne(namespace+"commentCount",bno);
    }

    @Override
    public int boardDeleteComment(Integer bno) {
        return session.delete(namespace+"boardDeleteComment",bno);
    }

    @Override
    public void updateProfile(String profile,String id) {
        HashMap map = new HashMap();
        map.put("profile",profile);
        map.put("id",id);
        session.update(namespace+"updateProfile",map);
    }


}
