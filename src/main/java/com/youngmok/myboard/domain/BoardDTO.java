package com.youngmok.myboard.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO {
    private BoardVO board;
    private List<ProjectFileVO> fList;

    public BoardDTO(BoardVO board) {
        this.board = board;
    }
}
