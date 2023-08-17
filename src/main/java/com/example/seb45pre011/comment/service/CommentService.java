package com.example.seb45pre011.comment.service;

import com.example.seb45pre011.comment.entity.Comment;
import com.example.seb45pre011.comment.mapper.CommentMapper;
import com.example.seb45pre011.comment.repository.CommentRepository;
import com.example.seb45pre011.exception.BusinessLogicException;
import com.example.seb45pre011.exception.ExceptionCode;
import com.example.seb45pre011.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Post post, Comment newComment){
        //postID를 통해 댓글 찾기
        newComment.setPost(post);
        return commentRepository.save(newComment);
    }
    public Comment updateComment(Comment comment){
        //  존재하는 댓글인지 확인
        Comment findComment = findVerifiedComment(comment.getCommentId());
        findComment.setContent(comment.getContent());
        return commentRepository.save(findComment);
    }
    public void deleteComment(long commentId){
        Comment comment = findVerifiedComment(commentId);
        commentRepository.delete(comment);
    }
    public List<Comment> findComments(Long cursor, int pageSize)  {
        return null;
    }
    public Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }
//    public void reorganizeCommentIds(Long postId) {
//        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAt(postId);
//        int newId = 1;
//
//        for (Comment comment : comments) {
//            comment.setId((long) newId++);
//            commentRepository.save(comment);
//        }
//    }
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPost_PostIdOrderByCreatedAt(postId);
    }
}
