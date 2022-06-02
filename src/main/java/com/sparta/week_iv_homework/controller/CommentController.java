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

    @GetMapping("/comment/list/{id}")
    public List<Comment> showComment(@PathVariable Long id){
        return commentRepository.findByPostNumOrderByCreatedAtDesc(id);
    }

    @DeleteMapping("/comment/{id}")
    public Long deleteReply(@PathVariable Long id) {
        Long postNum = commentRepository.getById(id).getPostNum();
        commentRepository.deleteById(id);
        return postNum;
    }

    @PutMapping("/comment/{id}")
    public Long updateReply(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        commentService.replyUpdate(id, requestDto);
        return commentRepository.getById(id).getPostNum();
    }

}
