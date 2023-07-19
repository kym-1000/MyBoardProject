package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.SearchCondition;

import java.util.ArrayList;
import java.util.List;

public interface BoardDAO {
    int insertBoard(BoardVO board);

    BoardVO selectBoardOne(int bno);

    int searchResultCnt(SearchCondition sc);

    List<BoardVO> searchSelectPage(SearchCondition sc);

    int boardDelete(Integer bno, String writer);


    int increaseViewCntUp(Integer bno);

    int boardUpdate(BoardVO board);

    int selectOneBno();

    int boardCommentCntUp(Integer bno);

    int boardCommentCntDown(Integer bno);

    void deleteAll();

    int selectCount();

    List<BoardVO> selectAll();

    int boardLikeUp(Integer bno);

    int boardListDelete(ArrayList<Integer> deleteList);

    List<BoardVO> selectNotice();
}
