package com.sparta.week_iv_homework.controller;

import com.sparta.week_iv_homework.domain.User;
import com.sparta.week_iv_homework.repository.UserRepository;
import com.sparta.week_iv_homework.dto.SignupRequestDto;
import com.sparta.week_iv_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user/login")
    public String login(){return "login";}

    @GetMapping("/user/signup")
    public String signup(){return "signup";}

    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto){
        if (!userService.checkDuplication(requestDto.getUsername())) {
            userService.createUserRecord(requestDto);}
        return "redirect:/user/login";
    }
}
