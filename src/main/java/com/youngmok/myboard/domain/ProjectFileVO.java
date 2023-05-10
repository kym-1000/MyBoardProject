package com.youngmok.myboard.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProjectFileVO {
    private String uuid;
    private String save_dir;
    private String file_name;
    private int file_type;
    private int bno;
    private int cno;
    private String user;
    private long file_size;
    private Date reg_date;

    public ProjectFileVO(String uuid, String fileName, String savePath) {
        this.uuid = uuid;
        this.file_name = fileName;
        this.save_dir = savePath;
    }
}
