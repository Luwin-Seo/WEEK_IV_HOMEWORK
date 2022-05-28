package com.sparta.week_iv_homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository <Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
}