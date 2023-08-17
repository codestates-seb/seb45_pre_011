package com.example.seb45pre011.comment.dto;

import com.example.seb45pre011.member.Member;
import com.example.seb45pre011.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


public class CommentDto {

    @NoArgsConstructor
    @Getter
    public static class PostDto{
        private String content;
        public PostDto(String content) {
            this.content = content;
        }
    }
    @NoArgsConstructor
    @Setter
    @Getter
    public static class PatchDto{
        private long commentId;
        private String content;
        public PatchDto(String content) {
            this.content = content;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Response{
        private long commentId;
        private String content;
        private Date createdAt;
        private Long memberId;
        private Long postId;
    }
}
