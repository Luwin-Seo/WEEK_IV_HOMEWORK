package com.sparta.week_iv_homework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long postNum;
    private String username;
    private String comment;

}