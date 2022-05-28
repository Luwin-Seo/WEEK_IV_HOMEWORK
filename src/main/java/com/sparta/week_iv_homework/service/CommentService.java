package com.sparta.week_iv_homework.service;

import com.sparta.week_iv_homework.domain.Comment;
import com.sparta.week_iv_homework.dto.CommentRequestDto;
import com.sparta.week_iv_homework.repository.CommentRepository;
import com.sparta.week_iv_homework.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void replySave(CommentRequestDto requestDto, UserDetailsImpl userDetails, Long id) {
        Comment comment = new Comment();
        comment.setPostNum(id);
        comment.setUsername(userDetails.getUsername());
        comment.setComment(requestDto.getComment());
        commentRepository.save(comment);
    }
}
