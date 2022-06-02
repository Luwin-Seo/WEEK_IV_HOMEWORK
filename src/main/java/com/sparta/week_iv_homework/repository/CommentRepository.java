package com.sparta.week_iv_homework.repository;


import com.sparta.week_iv_homework.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostNumOrderByCreatedAtDesc(Long postNum);
}
