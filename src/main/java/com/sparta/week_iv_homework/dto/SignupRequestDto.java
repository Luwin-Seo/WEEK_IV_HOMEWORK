package com.sparta.week_iv_homework.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9a-zA-Z]).{3,}", message = "아이디는 3자 이상으로 영문과 숫자만 포함할 수 있습니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9a-zA-Z]).{4,}", message = "비밀번호는 4자 이상으로 해 주세요")
    private String password;

    @NotBlank
    private String passwordCheck;

    public SignupRequestDto(String username, String password, String passwordCheck) {
        this.username = username;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
