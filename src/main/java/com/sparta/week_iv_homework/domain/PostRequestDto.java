package com.sparta.week_iv_homework.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PostRequestDto {
    private String title;
    private String username;
    private String password;
    private String comment;
}
