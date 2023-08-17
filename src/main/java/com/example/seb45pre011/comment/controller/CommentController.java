package com.example.seb45pre011.comment.controller;

import com.example.seb45pre011.comment.dto.CommentDto;
import com.example.seb45pre011.comment.entity.Comment;
import com.example.seb45pre011.comment.mapper.CommentMapper;
import com.example.seb45pre011.comment.service.CommentService;
import com.example.seb45pre011.post.entity.Post;
import com.example.seb45pre011.post.repository.PostRepository;
import com.example.seb45pre011.post.service.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            @RequestBody CommentDto.PostDto postDto){
        // 존재하는 post인지 확인
        Post post = postService.findVerifiedPost(postId);
        Comment comment = commentService.createComment(post,mapper.commentPostDtoToComment(postDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.commentToResponseDTO(comment));
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(
            @PathVariable("post-id") Long postId,
            @PathVariable("comment-id") Long commentId,
            @RequestBody CommentDto.PatchDto patchDto) {

        // 여기서 patchDto를 사용하여 댓글 업데이트 로직을 구현하세요.
        // comment_id를 사용하여 해당 댓글을 찾고, patchDto의 내용으로 업데이트합니다.
        // comment_id와 post_id를 사용하여 해당 댓글을 찾습니다.

        /*
        TODO 로그인 중인 사용자 정보를 받기
        TODO 받은 사용자 정보와 comments 테이블에 저장되어 있는 userId를 비교
        TODO 검증되면 로직 진행, 검증 실패 -> 권한없음 에러
        TODO 댓글 수정 시 userId를 DB에 저장 후, 응답 바디에 반환 */

        Comment updatedComment = commentService.updateComment(commentId,mapper.commentPatchDtoToComment(patchDto));
        // 댓글의 post_id와 주어진 postId를 비교하여 검증합니다.
        if (updatedComment.getPost().getPostId() != postId) {
            return new ResponseEntity<>("You don't have permission to update this comment.", HttpStatus.FORBIDDEN);
        }
        // 변경된 댓글을 저장하고 업데이트된 댓글을 반환합니다.
        return new ResponseEntity<>(mapper.commentToResponseDTO(updatedComment), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto.Response>> getComments(
            @PathVariable("post-id") Long postId,
            @RequestParam(value = "cursor", required = false ,defaultValue = "0") Long cursor,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
       // TODO 게시글의 댓글을 무한 스크롤로 가져오는 기능
        // TODO 추가 구현 사항 -> 답변 정보에 userID 담기
        postService.findVerifiedPost(postId);
        List<Comment> comments= commentService.findComments(postId,cursor,pageSize);
        Long nextCursor = null;
        // 댓글이 있고, 받아온 댓글의 양이 pageSize보다 클 때,
        if (!comments.isEmpty() && comments.size() >= pageSize) {
            nextCursor = comments.get(comments.size() - 1).getCommentId();
        }

        List<CommentDto.Response> responseDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto.Response dto = mapper.commentToResponseDTO(comment);
            responseDTOs.add(dto);
        }

        HttpHeaders headers = new HttpHeaders();
        if (nextCursor != null) {
            headers.add("X-Next-Cursor", nextCursor.toString());
        }
        return new ResponseEntity<>(responseDTOs, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(
            @PathVariable("post-id") Long postId,
            @PathVariable("comment-id") Long commentId){
        // TODO 글을 작성한 유저 or 관리자인지 판별 후 로직 진행
        // 일치 x -> 권한 없음
        // 일단 존재하는 comment인지 부터 확인
        Comment existingComment = commentService.findVerifiedComment(commentId);

        // 댓글의 post_id와 주어진 postId를 비교하여 검증합니다.
        if (existingComment.getPost().getPostId() != postId) {
            return new ResponseEntity<>("You don't have permission to delete this comment.", HttpStatus.FORBIDDEN);
        }

        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}