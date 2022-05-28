package com.sparta.week_iv_homework.domain;


import com.sparta.week_iv_homework.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String comment;


    public Post (PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.comment = requestDto.getComment();
    }
    public void update (PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.comment = requestDto.getComment();
    }
}
