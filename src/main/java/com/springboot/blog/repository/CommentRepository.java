package com.springboot.blog.repository;

import com.springboot.blog.models.blog.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
