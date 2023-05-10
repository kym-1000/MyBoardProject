package com.youngmok.myboard.dao;

import com.youngmok.myboard.domain.CommentDTO;

import java.util.List;

public interface CommentDAO {
    int commentInsert(CommentDTO comment);

    List<CommentDTO> commentSelectList(Integer bno);

    int deleteComment(Integer cno, Integer bno);

    void deleteAll();

    int commentCount(Integer bno);
}
