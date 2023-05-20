package com.youngmok.myboard.service;

import com.youngmok.myboard.domain.BoardDTO;
import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.SearchCondition;

import java.util.List;

public interface BoardService  {


    BoardVO boardlist(int bno);

    int getSearchResultCnt(SearchCondition sc);

    List<BoardVO> getSearchResultPage(SearchCondition sc);

    BoardDTO boardread(Integer bno);

    int deleteOne(Integer bno, String writer);

    int modifyOne(BoardVO board);

    int boardadd(BoardDTO boardDTO);

    int modifyBoard(BoardDTO boardDTO);

    int boardlike(Integer bno);
}
