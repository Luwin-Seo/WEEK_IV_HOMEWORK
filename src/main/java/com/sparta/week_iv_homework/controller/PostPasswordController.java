package com.sparta.week_iv_homework.controller;

import com.sparta.week_iv_homework.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostPasswordController {
    private final PostRepository postRepository;

}
