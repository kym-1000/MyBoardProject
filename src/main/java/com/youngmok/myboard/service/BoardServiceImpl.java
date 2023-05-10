package com.youngmok.myboard.service;

import com.youngmok.myboard.dao.BoardDAO;
import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.domain.BoardDTO;
import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardDAO BDAO;

    @Autowired
    private FileDAO FDAO;


    @Override
    public int boardadd(BoardDTO boardDTO) {

        int isOk = BDAO.insertBoard(boardDTO.getBoard());
        // 기존 게시글에 대한 내용을 DB에 저장

        if(isOk > 0 && boardDTO.getFList().size() > 0) {
            int bno = BDAO.selectOneBno(); // 방금 넣었던 board 객체가 db에 저장된 후
            // 가장 큰 bno 가져오기

            for(ProjectFileVO fvo : boardDTO.getFList()) {
                fvo.setBno(bno);
                System.out.println("\" insert file : \"+fvo.toString() = " + " insert file : " + fvo.toString());
                isOk *= FDAO.insertFile(fvo);
            }
        }

        return isOk;
    }

    @Override
    public int modifyBoard(BoardDTO boardDTO) {

        int isOk = BDAO.boardUpdate(boardDTO.getBoard());

        if(isOk > 0 && boardDTO.getFList().size() > 0) {
            int bno = BDAO.selectOneBno(); // 방금 넣었던 board 객체가 db에 저장된 후
            // 가장 큰 bno 가져오기

            for(ProjectFileVO fvo : boardDTO.getFList()) {
                fvo.setBno(bno);
                isOk *= FDAO.boardFileModify(fvo);
            }
        }

        return isOk;

    }

    @Override
    public BoardVO boardlist(int bno) {
        return BDAO.selectBoardOne(bno);
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) {
        return BDAO.searchResultCnt(sc);
    }

    @Override
    public List<BoardVO> getSearchResultPage(SearchCondition sc) {
        return BDAO.searchSelectPage(sc);
    }

    @Override
    public BoardDTO boardread(Integer bno) {
        int isOk = BDAO.increaseViewCntUp(bno);
        if(isOk>0){
            System.out.println("\"조회수 상승 여부\" = " + "조회수 상승 성공");
        } else {
            System.out.println("\"조회수 상승 여부\" = " + "조회수 상승 실패");
        }

        return new BoardDTO(BDAO.selectBoardOne(bno),FDAO.selectFileList(bno));
    }

    @Override
    public int deleteOne(Integer bno, String writer) {
        return BDAO.boardDelete(bno,writer);
    }

    @Override
    public int modifyOne(BoardVO board) {
        return BDAO.boardUpdate(board);
    }
}
