package com.youngmok.myboard.service;


import com.youngmok.myboard.dao.BoardDAO;
import com.youngmok.myboard.dao.CommentDAO;
import com.youngmok.myboard.domain.BoardDTO;
import com.youngmok.myboard.domain.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentDAO CDAO;

    @Autowired
    BoardDAO BDAO;

    @Override
    public int register(CommentDTO comment) {
        int bno = comment.getBno();  // 댓글이 생긴 게시글 번호를 가져온다.

        // 게시글에 comment cnt 업!
        int isOk = BDAO.boardCommentCntUp(bno);

        if(isOk>0){
            System.out.println("성공!");
        } else{
            System.out.println("\"실패\" = " + "실패");
        }

        return CDAO.commentInsert(comment);
    }

    @Override
    public List<CommentDTO> getList(int bno) {
        return CDAO.commentSelectList(bno);
    }

    @Override
    public int remove(Integer cno, Integer bno) {

       int isOk = BDAO.boardCommentCntDown(bno);  // 댓글를 하나식 삭제하면 게시글에 달린 댓글수 정보도 줄어들도록

        if(isOk>0){
            System.out.println("성공!");
        } else{
            System.out.println("실패!");
        }

        return CDAO.deleteComment(cno,bno);
    }


}
