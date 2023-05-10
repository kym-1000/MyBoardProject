package com.youngmok.myboard.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private UserVO userVO;
    private List<ProjectFileVO> fList;
}
