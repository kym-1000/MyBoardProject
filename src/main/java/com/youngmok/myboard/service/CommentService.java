package com.youngmok.myboard.service;


import com.youngmok.myboard.domain.CommentDTO;

import java.util.List;

public interface CommentService {

    int register(CommentDTO comment);


    List<CommentDTO> getList(int bno);

    int remove(Integer cno, Integer bno);
}
