package com.example.seb45pre011.comment.controller;

import com.example.seb45pre011.comment.dto.CommentDto;
import com.example.seb45pre011.comment.entity.Comment;
import com.example.seb45pre011.comment.mapper.CommentMapper;
import com.example.seb45pre011.comment.service.CommentService;
import com.example.seb45pre011.post.entity.Post;
import com.example.seb45pre011.post.repository.PostRepository;
import com.example.seb45pre011.post.service.PostService;
import com.example.seb45pre011.response.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{post-id}/comments")
@Validated
public class CommentController {
    // TODO : 후에 post와 user가 완성되면 user검증과 post검증로직도 들어가야한다.
    private final CommentService commentService;
    private final PostService postService;
    private final CommentMapper mapper;

    public CommentController(CommentService commentService, PostService postService, PostRepository postRepository, CommentMapper mapper) {
        this.commentService = commentService;
        this.postService = postService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postComment(
            @PathVariable("post-id") Long postId,
            @RequestBody CommentDto.PostDto postDto) {
        Post post = postService.findVerifiedPost(postId);
        Comment comment = commentService.createComment(post,mapper.commentPostDtoToComment(postDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(
            @PathVariable("post-id") Long postId,
            @PathVariable("comment-id") Long commentId,
            @RequestBody CommentDto.PatchDto patchDto) {

        // 여기서 patchDto를 사용하여 댓글 업데이트 로직을 구현하세요.
        // comment_id를 사용하여 해당 댓글을 찾고, patchDto의 내용으로 업데이트합니다.
        // comment_id와 post_id를 사용하여 해당 댓글을 찾습니다.
        Comment existingComment = commentService.findVerifiedComment(commentId);

        // 댓글의 post_id와 주어진 postId를 비교하여 검증합니다.
        if (existingComment.getPost().getPostId() != postId) {
            return new ResponseEntity<>("You don't have permission to update this comment.", HttpStatus.FORBIDDEN);
        }

        // 댓글 내용을 업데이트합니다.
        existingComment.setContent(patchDto.getContent());

        // 변경된 댓글을 저장하고 업데이트된 댓글을 반환합니다.
        Comment updatedComment = commentService.updateComment(existingComment);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.commentToCommentResponse(updatedComment)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable("post-id") Long postId,
            @RequestParam(value = "cursor",  defaultValue = "0") Long cursor,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
       // TODO 게시글의 댓글을 무한 스크롤로 가져오는 기능
        return null;
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(
            @PathVariable("post-id") Long postId,
            @PathVariable("comment-id") Long commentId){
        // comment_id와 post_id를 사용하여 해당 댓글을 찾습니다.
        Comment existingComment = commentService.findVerifiedComment(commentId);

        // 댓글의 post_id와 주어진 postId를 비교하여 검증합니다.
        if (existingComment.getPost().getPostId() != postId) {
            return new ResponseEntity<>("You don't have permission to delete this comment.", HttpStatus.FORBIDDEN);
        }

        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}