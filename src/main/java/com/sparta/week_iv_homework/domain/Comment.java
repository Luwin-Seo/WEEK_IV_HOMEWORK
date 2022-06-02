package com.sparta.week_iv_homework.domain;


import com.sparta.week_iv_homework.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long postNum;

    @Column
    private String username;

    @Column
    private String comment;

    public void update(CommentRequestDto requestDto) {
        this.postNum = requestDto.getPostNum();
        this.username = requestDto.getUsername();
        this.comment = requestDto.getComment();
    }
}
