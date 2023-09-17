package com.youngmok.myboard.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDTO {
    private BoardVO board;
    private ProjectFileVO file;
    private String uuid_file_name;

    public BoardDTO(BoardVO board) {
        this.board = board;
    }

    public BoardDTO(BoardVO board,String uuid_file_name) {
        this.board = board;
        this.uuid_file_name = uuid_file_name;
    }

    public BoardDTO(BoardVO selectBoardOne, ProjectFileVO selectFileList) {
        this.board = selectBoardOne;
        this.file = selectFileList;
    }

}