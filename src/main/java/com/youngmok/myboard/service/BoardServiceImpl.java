package com.youngmok.myboard.service;

import com.youngmok.myboard.dao.BoardDAO;
import com.youngmok.myboard.dao.CommentDAO;
import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.domain.BoardDTO;
import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.SearchCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final BoardDAO BDAO;
    private final FileDAO FDAO;
    private final CommentDAO CDAO;

    @Autowired  // 생성자를 통하여 필드주입
    public BoardServiceImpl(BoardDAO BDAO, FileDAO FDAO,CommentDAO CDAO) {
        this.BDAO = BDAO;
        this.FDAO = FDAO;
        this.CDAO = CDAO;
    }


    // 파일처리
    public int insertFile(BoardDTO boardDTO, Integer bno) {
        int isOk = 1;
        boardDTO.getFile().setBno(bno);
        boardDTO.getFile().setFile_type(1);
        logger.info("fvo = " + boardDTO.getFile());
        isOk *= FDAO.insertFile(boardDTO.getFile());
        return isOk;
    }

    // 게시글 쓰기
    @Override
    public int boardadd(BoardDTO boardDTO) {
        logger.info("board : " + boardDTO.getBoard());
        logger.info("getFList : " + boardDTO.getFile());
        int isOk = BDAO.insertBoard(boardDTO.getBoard());
        // 기존 게시글에 대한 내용을 DB에 저장
        if (isOk > 0 && boardDTO.getFile() != null) { // 게시글이 성공적으로 등록이 되고 파일이 존재할경우
            int bno = BDAO.selectOneBno(); // 해당 bno
            isOk *= insertFile(boardDTO, bno);
        }
        logger.info("isOk : "+isOk);
        return isOk;
    }

    // 게시글 수정
    @Override
    public int modifyBoard(BoardDTO boardDTO) {

        int isOk = BDAO.boardUpdate(boardDTO.getBoard());  // 게시글 수정

        if (isOk > 0 && boardDTO.getFile() != null) { // 게시글 수정에 성공했고 수정해야할 파일이 존재한다면...
            int bno = boardDTO.getBoard().getBno(); // 해당 bno
            if (FDAO.fileCount(bno) == 0) {  // 기존 게시글에 파일이 존재하지 않다면...
                isOk *= insertFile(boardDTO, bno);
            } else { // 기존 게시글에 파일이 존재한다면...
                boardDTO.getFile().setBno(bno);
                boardDTO.getFile().setFile_type(1);
                logger.info("fvo nod : "+boardDTO.getFile());
                isOk *= FDAO.boardFileModify(boardDTO.getFile());
            }
        }
        return isOk;
    }

    // 게시글 좋아요 상승
    @Override
    public int boardlike(Integer bno) {
        return BDAO.boardLikeUp(bno);
    }


    // 관리자 게시글 다중 삭제
    @Override
    public int adBoardListDelete(ArrayList<Integer> deleteList) {

        for (Integer bno : deleteList) {
            boardFileCommentDelete(bno);
        }

        return BDAO.boardListDelete(deleteList);
    }

    // 공지사항들 불러오기
    @Override
    public List<BoardVO> getNoticeList() {
        return BDAO.selectNotice();
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
    public List<BoardDTO> getSearchResultPage(SearchCondition sc) {
        return BDAO.searchSelectPage(sc);
    }

    @Override
    public BoardDTO boardread(Integer bno) {
        int isOk = BDAO.increaseViewCntUp(bno);  // 게시글 조회수 상승
        if (isOk > 0) {
            logger.info("조회수 상승 성공!");
        } else {
            logger.warn("조회수 상승 실패");
        }

        return new BoardDTO(BDAO.selectBoardOne(bno),FDAO.selectFileList(bno));
    }

    // 게시글 삭제
    @Override
    public int deleteOne(Integer bno, String writer) {

        boardFileCommentDelete(bno); // 파일 댓글 삭제 메서드 호출

        return BDAO.boardDelete(bno, writer);
    }

    @Override
    public int modifyOne(BoardVO board) {
        return BDAO.boardUpdate(board);
    }

    // 게시글 삭제시 파일 및 댓글도 같이 삭제
    private void boardFileCommentDelete(int bno) {
        if (FDAO.fileCount(bno) > 0) {  // 파일이 존재한다면
            FDAO.deleteFile(bno);  // 파일 삭제
        }

        if (CDAO.commentCount(bno) > 0) {       // 댓글이 존재한다면
            CDAO.boardDeleteComment(bno);   // 댓글 삭제
        }
    }
}
