package com.sparta.week_iv_homework.controller;

import com.sparta.week_iv_homework.domain.User;
import com.sparta.week_iv_homework.repository.UserRepository;
import com.sparta.week_iv_homework.dto.SignupRequestDto;
import com.sparta.week_iv_homework.security.UserDetailsImpl;
import com.sparta.week_iv_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/user/loginView")
    public String login(){return "login";}

    @GetMapping("/user/signup")
    public String signup(){return "signup";}

    @PostMapping("/user/signup")
    public String registerUser(@Valid SignupRequestDto requestDto, Errors errors, Model model){
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if (found.isPresent()) {
            model.addAttribute("valid_username", "중복되는 아이디가 존재합니다.");
            return "signup";
        }

        else if (requestDto.getPassword().contains(requestDto.getUsername())) {
            model.addAttribute("valid_password", "비밀번호는 아이디와 같은 값을 포함할 수 없습니다.");

            return "signup";
        }
        else if (errors.hasErrors()) {

            Map<String, String> validatorResult = userService.validateHandling(errors);

            System.out.println(validatorResult.keySet());

            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));}

            return "signup";
        }
        else if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            model.addAttribute("passwordCheck","비밀번호가 일치하지 않습니다.");

            return "signup";
        }

        userService.createUserRecord(requestDto);
        return "redirect:/user/loginView";
    }

}
