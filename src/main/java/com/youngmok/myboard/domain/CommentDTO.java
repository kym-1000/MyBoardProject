package com.youngmok.myboard.domain;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class CommentDTO {
    private int cno;
    private int bno;
    private int pcno;
    private String writer;
    private String content;
    private String reg_date;
    private String image_file;

    public CommentDTO(int bno,String writer,String content){
        this.bno = bno;
        this.writer = writer;
        this.content = content;
    }

    public CommentDTO(int bno,int pcno,String writer,String content){
        this.bno = bno;
        this.pcno = pcno;
        this.writer = writer;
        this.content = content;
    }



}
