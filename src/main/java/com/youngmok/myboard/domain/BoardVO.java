package com.youngmok.myboard.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BoardVO {
    private int bno;
    private String title;
    private String content;
    private String writer;
    private int cnt;
    private int comment_cnt;
    private int like;
    private Date reg_date;

    public BoardVO(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }


}
