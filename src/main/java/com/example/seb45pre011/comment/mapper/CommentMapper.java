package com.example.seb45pre011.comment.mapper;

import com.example.seb45pre011.comment.dto.CommentDto;
import com.example.seb45pre011.comment.entity.Comment;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDtoToComment(CommentDto.PostDto postDto);
    Comment commentPatchDtoToComment(CommentDto.PatchDto patchDto);
    CommentDto.Response commentToCommentResponse(Comment comment);
}
