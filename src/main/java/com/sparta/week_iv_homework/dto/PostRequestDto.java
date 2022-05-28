package com.sparta.week_iv_homework.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class PostRequestDto {
    private String title;
    private String username;
    private String password;
    private String content;
}
