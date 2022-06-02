package com.sparta.week_iv_homework.controller;


import com.sparta.week_iv_homework.domain.Post;
import com.sparta.week_iv_homework.dto.PostRequestDto;
import com.sparta.week_iv_homework.repository.PostRepository;
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
    public Post createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        post.setUsername(userDetails.getUsername());
        return postRepository.save(post);
    }

    @GetMapping("/post")
    public boolean checkLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails != null;
    }

    @GetMapping("/list")
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByCreatedAtAsc();
    }

    @GetMapping ("/mod/{id}")
    public boolean userCheck(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
        return post.getUsername().equals(userDetails.getUsername());
    }

    @DeleteMapping("/mod/{id}")
    public Long deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
    }

    @PutMapping("/mod/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        postService.update(id, requestDto);
        return id;
    }

    @GetMapping("/post/user")
    public boolean usernameCheck(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String username) {
        System.out.println(username);
        System.out.println(userDetails.getUsername().equals(username));
        return userDetails.getUsername().equals(username);
    }

}
