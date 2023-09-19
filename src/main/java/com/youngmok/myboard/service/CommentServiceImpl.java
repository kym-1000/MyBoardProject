package com.youngmok.myboard.service;


import com.youngmok.myboard.dao.BoardDAO;
import com.youngmok.myboard.dao.CommentDAO;
import com.youngmok.myboard.domain.CommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final BoardDAO BDAO;
    private final CommentDAO CDAO;

    @Autowired  // 생성자를 통하여 필드주입
    public CommentServiceImpl(BoardDAO BDAO, CommentDAO CDAO) {
        this.BDAO = BDAO;
        this.CDAO = CDAO;
    }

    // 댓글 등록
    @Override
    public int register(CommentDTO comment) {
        int bno = comment.getBno();  // 댓글이 생긴 게시글 번호를 가져온다.
        // 게시글에 comment cnt 업!
        int isOk = BDAO.boardCommentCntUp(bno);

        if(isOk>0){
           logger.info("게시글 댓글 카운트 상승 성공!");
        } else{
            logger.info("게시글 댓글 카운트 상승 실패!");
        }

        return CDAO.commentInsert(comment);
    }

    // 댓글 리스트
    @Override
    public List<CommentDTO> getList(int bno) {
        return CDAO.commentSelectList(bno);
    }

    
    // 댓글 삭제
    @Override
    public int remove(Integer cno, Integer bno) {

       int isOk = BDAO.boardCommentCntDown(bno);  // 댓글를 하나식 삭제하면 게시글에 달린 댓글수 정보도 줄어들도록

        if(isOk>0){
            logger.info("게시글 댓글 카운트 감소 성공!");
        } else{
            logger.info("게시글 댓글 카운트 감소 성공!");
        }

        return CDAO.deleteComment(cno,bno);
    }


}
