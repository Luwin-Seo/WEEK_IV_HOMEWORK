package com.sparta.week_iv_homework.controller;

import com.sparta.week_iv_homework.domain.Comment;
import com.sparta.week_iv_homework.dto.CommentRequestDto;
import com.sparta.week_iv_homework.repository.CommentRepository;
import com.sparta.week_iv_homework.security.UserDetailsImpl;
import com.sparta.week_iv_homework.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/comment/{id}")
    public String submitReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto, @PathVariable Long id) {
        commentService.replySave(requestDto,userDetails,id);
        return null;
    }

    @GetMapping("/comment/{id}")
    public List<Comment> showComment(@PathVariable Long id){
        return commentRepository.findByPostNum(id);
    }
}
