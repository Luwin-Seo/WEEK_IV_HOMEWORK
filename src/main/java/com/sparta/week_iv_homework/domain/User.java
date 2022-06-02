package com.sparta.week_iv_homework.domain;


import com.sparta.week_iv_homework.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private Long KakaoId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) //DB에 저장될때 스트링으로 변환된다는 뜻
    private UserRoleEnum role;

    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.KakaoId = null;
        this.role = UserRoleEnum.USER;
    }

    public User(String username, String password, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.KakaoId = kakaoId;
        this.role = UserRoleEnum.USER;
    }

}
