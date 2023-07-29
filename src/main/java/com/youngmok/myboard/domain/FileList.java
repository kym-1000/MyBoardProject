package com.youngmok.myboard.domain;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class FileList {
    private ProjectFileVO file;
    private String profile;
}
