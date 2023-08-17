package com.example.seb45pre011.comment.repository;

import com.example.seb45pre011.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost_PostIdOrderByCreatedAt(Long postId);
}
