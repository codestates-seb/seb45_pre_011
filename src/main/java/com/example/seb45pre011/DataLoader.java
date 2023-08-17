package com.example.seb45pre011;

import com.example.seb45pre011.comment.entity.Comment;
import com.example.seb45pre011.comment.repository.CommentRepository;
import com.example.seb45pre011.post.entity.Post;
import com.example.seb45pre011.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public DataLoader(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 게시물 생성
        Post post1 = new Post("첫 번째 게시물", "첫 번째 게시물 내용입니다.");
        Post post2 = new Post("두 번째 게시물", "두 번째 게시물 내용입니다.");
        postRepository.save(post1);
        postRepository.save(post2);
    }
}