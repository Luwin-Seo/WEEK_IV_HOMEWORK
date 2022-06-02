package com.sparta.week_iv_homework.service;

import com.sparta.week_iv_homework.domain.Post;
import com.sparta.week_iv_homework.dto.PostRequestDto;
import com.sparta.week_iv_homework.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public void update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        postRepository.save(post);
    }
}
