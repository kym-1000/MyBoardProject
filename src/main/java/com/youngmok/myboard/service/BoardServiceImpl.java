package com.youngmok.myboard.service;

import com.youngmok.myboard.dao.BoardDAO;
import com.youngmok.myboard.dao.CommentDAO;
import com.youngmok.myboard.dao.FileDAO;
import com.youngmok.myboard.domain.BoardDTO;
import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardDAO BDAO;

    @Autowired
    private FileDAO FDAO;

    @Autowired
    private CommentDAO CDAO;


    @Override
    public int boardadd(BoardDTO boardDTO) {

        int isOk = BDAO.insertBoard(boardDTO.getBoard());
        // 기존 게시글에 대한 내용을 DB에 저장

        if(isOk > 0 && boardDTO.getFList().size() > 0) {
            int bno = BDAO.selectOneBno(); // 해당 bno

            for(ProjectFileVO fvo : boardDTO.getFList()) {
                fvo.setBno(bno);
                fvo.setFile_type(1);
                System.out.println("fvo new = " + fvo);
                isOk *= FDAO.insertFile(fvo);
            }
        }

        return isOk;
    }

    @Override
    public int modifyBoard(BoardDTO boardDTO) {

        int isOk = BDAO.boardUpdate(boardDTO.getBoard());

        if(isOk > 0 && boardDTO.getFList().size() > 0) {
            int bno = boardDTO.getBoard().getBno(); // 해당 bno

            System.out.println("bno = " + bno);


            if(FDAO.fileCount(bno)==0){  // 기존 게시글에 파일이 존재하지 않다면...
                for(ProjectFileVO fvo : boardDTO.getFList()) {
                    fvo.setBno(bno);
                    fvo.setFile_type(1);
                    System.out.println("fvo new mod = " + fvo);
                    isOk *= FDAO.insertFile(fvo);
                }
            } else{
                for(ProjectFileVO fvo : boardDTO.getFList()) {
                    fvo.setBno(bno);
                    fvo.setFile_type(1);
                    System.out.println("fvo mod = " + fvo);
                    isOk *= FDAO.boardFileModify(fvo);
                }
            }

        }

        return isOk;

    }

    @Override
    public int boardlike(Integer bno) {
        return BDAO.boardLikeUp(bno);
    }

    @Override
    public int adBoardListDelete(ArrayList<Integer> deleteList) {

        for (Integer bno : deleteList) {
            boardFileCommentDelete(bno);
        }

        return BDAO.boardListDelete(deleteList);
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
        int isOk = BDAO.increaseViewCntUp(bno);  // 게시글 조회수 상승
        if(isOk>0){
            System.out.println("\"조회수 상승 여부\" = " + "조회수 상승 성공");
        } else {
            System.out.println("\"조회수 상승 여부\" = " + "조회수 상승 실패");
        }

        return new BoardDTO(BDAO.selectBoardOne(bno),FDAO.selectFileList(bno));
    }


    @Override
    public int deleteOne(Integer bno, String writer) {

        boardFileCommentDelete(bno);

        return BDAO.boardDelete(bno,writer);
    }

    @Override
    public int modifyOne(BoardVO board) {
        return BDAO.boardUpdate(board);
    }

    private void boardFileCommentDelete(int bno){
        if(FDAO.fileCount(bno)>0){  // 파일이 존재한다면
            FDAO.deleteFile(bno);  // 파일 삭제
        }

        if(CDAO.commentCount(bno)>0){       // 댓글이 존재한다면
            CDAO.boardDeleteComment(bno);   // 댓글 삭제
        }
    }
}
