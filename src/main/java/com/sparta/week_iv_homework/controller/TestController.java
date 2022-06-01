package com.sparta.week_iv_homework.controller;

import com.sparta.week_iv_homework.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestController {

    @PostMapping("/test")
    public String apiTest (@Valid @AuthenticationPrincipal UserDetailsImpl userDetails, Errors errors) {
        String testAnswer;
        if(userDetails == null) {testAnswer = "에러가 있습니다";}
        else {testAnswer = "정상작동합니다";}
            return testAnswer;
    }
}
