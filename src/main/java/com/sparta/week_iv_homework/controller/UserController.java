package com.sparta.week_iv_homework.controller;

import com.sparta.week_iv_homework.domain.User;
import com.sparta.week_iv_homework.repository.UserRepository;
import com.sparta.week_iv_homework.dto.SignupRequestDto;
import com.sparta.week_iv_homework.security.UserDetailsImpl;
import com.sparta.week_iv_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user")
    @ResponseBody
    public Boolean usernameCheck(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String username) {
        System.out.println(username);
        System.out.println(userDetails.getUsername().equals(username));
        return userDetails.getUsername().equals(username);
    }

}
