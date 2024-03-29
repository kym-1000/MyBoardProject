package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.BoardDTO;
import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class BoardDAOImpl implements BoardDAO{

    private final SqlSession session;

    @Autowired
    public BoardDAOImpl(SqlSession session) {
        this.session = session;
    }
    private static final String namespace = "com.youngmok.myboard.dao.BoardMapper.";

    @Override
    public int insertBoard(BoardVO board) {
        return session.insert(namespace+"insertBoard",board);
    }

    @Override
    public BoardVO selectBoardOne(int bno) {
        return session.selectOne(namespace+"selectBoardOne",bno);
    }
    @Override
    public int searchResultCnt(SearchCondition sc) {
        return session.selectOne(namespace+"searchResultCnt", sc);
    }

    @Override
    public List<BoardDTO> searchSelectPage(SearchCondition sc) {
        return session.selectList(namespace+"searchSelectPage",sc);
    }

    @Override
    public void deleteAll() {
        session.delete(namespace + "deleteAll");
    }

    @Override
    public int selectCount() {
        return session.selectOne(namespace+"selectCount");
    }

    @Override
    public List<BoardVO> selectAll() {
       return session.selectList(namespace + "selectAll");
    }

    @Override
    public int boardLikeUp(Integer bno) {
        return session.update(namespace+"boardLikeUp",bno);
    }

    @Override
    public int boardListDelete(ArrayList<Integer> deleteList) {
        return session.delete(namespace+"boardListDelete",deleteList);
    }

    @Override
    public List<BoardVO> selectNotice() {
        return session.selectList(namespace+"selectNotice");
    }


    @Override
    public int boardDelete(Integer bno, String writer) {
        HashMap map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace+"delete",map);
    }

    @Override
    public int increaseViewCntUp(Integer bno) {
       return session.update(namespace + "increaseViewCnt",bno);
    }

    @Override
    public int boardUpdate(BoardVO board) {
        return session.update(namespace+"update",board);
    }

    @Override
    public int selectOneBno() {
        return session.selectOne(namespace+"selectOneBno");
    }

    @Override
    public int boardCommentCntUp(Integer bno) {
        return session.update(namespace + "boardCommentCntUp",bno);
    }

    @Override
    public int boardCommentCntDown(Integer bno) {
        return session.update(namespace+"boardCommentCntDown",bno);
    }




}
