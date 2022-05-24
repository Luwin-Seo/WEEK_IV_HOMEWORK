package com.sparta.week_iv_homework.controller;


import com.sparta.week_iv_homework.domain.Post;
import com.sparta.week_iv_homework.domain.PostRepository;
import com.sparta.week_iv_homework.domain.PostRequestDto;
import com.sparta.week_iv_homework.domain.SHA256;
import com.sparta.week_iv_homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @PostMapping("/post")
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        post.setPassword(SHA256.sha256(requestDto.getPassword()));
        return postRepository.save(post);
    }

    @GetMapping("/post")
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/post/{id}")
    public boolean PasswordCheck(@PathVariable Long id, @RequestParam String paramPassword) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
        if (post.getPassword().equals(SHA256.sha256(paramPassword))) return true;
        else return false;
    }

    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
    }

    @PutMapping("/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        requestDto.setPassword(SHA256.sha256(requestDto.getPassword()));
        postService.update(id, requestDto);
        return id;
    }
}
