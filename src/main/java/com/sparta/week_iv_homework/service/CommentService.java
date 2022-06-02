package com.sparta.week_iv_homework.service;


import com.sparta.week_iv_homework.domain.Comment;
import com.sparta.week_iv_homework.dto.CommentRequestDto;
import com.sparta.week_iv_homework.repository.CommentRepository;
import com.sparta.week_iv_homework.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Transactional
    public void replyUpdate(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
        comment.update(requestDto);

    }

}
