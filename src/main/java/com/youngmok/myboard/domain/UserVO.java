package com.youngmok.myboard.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserVO {
    @Pattern(regexp = "^[a-zA-Z0-9]{5,12}$", message = "id는 5~12자리 영대소문자 숫자 조합이어야합니다.")
    private String id;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{6,12}$", message = "패스워드는 6~12자리 영대소문자 특수문자 및 숫자 조합이어야합니다.")
    private String pwd;
    @Pattern(regexp = "^[가-힣]{2,6}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$", message = "이름은 2~6글자 한글이름 혹은 영문이름이어야합니다.")
    private String name;
    @Email(message = "이메일형식이 맞지 않습니다.")
    private String email;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birth;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date reg_date;
    private String profile;

    public UserVO(String s, String s1) {
    }
}
