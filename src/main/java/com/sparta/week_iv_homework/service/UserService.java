package com.sparta.week_iv_homework.service;

import com.sparta.week_iv_homework.domain.User;
import com.sparta.week_iv_homework.domain.UserRoleEnum;
import com.sparta.week_iv_homework.repository.UserRepository;
import com.sparta.week_iv_homework.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        System.out.println(validatorResult);
        return validatorResult;
    }

    public void createUserRecord(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(username,password);
        user.setRole(UserRoleEnum.USER);
        userRepository.save(user);
    }

    public boolean checkDuplication(String username) {
        Optional<User> found = userRepository.findByUsername(username);
        return found.isPresent();
    }
}
