package com.sparta.week_iv_homework.controller;


import com.sparta.week_iv_homework.domain.Post;
import com.sparta.week_iv_homework.repository.PostRepository;
import com.sparta.week_iv_homework.dto.PostRequestDto;
import com.sparta.week_iv_homework.domain.SHA256;
import com.sparta.week_iv_homework.security.UserDetailsImpl;
import com.sparta.week_iv_homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String checkLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        return username + "님의 글이 게시되었습니다";
    }

    @GetMapping("/list")
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping("/mod/{id}")
    public boolean PasswordCheck(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
        System.out.println(requestDto.getPassword());
        System.out.println(post.getPassword());
        System.out.println(SHA256.sha256(requestDto.getPassword()));
        if (post.getPassword().equals(SHA256.sha256(requestDto.getPassword()))) return true;
        else return false;
    }

    @DeleteMapping("/mod/{id}")
    public Long deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
    }

    @PutMapping("/mod/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        requestDto.setPassword(SHA256.sha256(requestDto.getPassword()));
        postService.update(id, requestDto);
        return id;
    }
}
