package com.example.springcrud2.dto;

import com.example.springcrud2.entity.Board;
import com.example.springcrud2.entity.Comment;
import com.example.springcrud2.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long id;
    private String content;
    private String name;
    private Long like;

    public CommentResponseDto(Comment comment, Long commentLikeCount) {
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.id = comment.getId();
        this.content = comment.getContent();
        this.name = comment.getMember().getName();
        this.like = commentLikeCount;
    }
}
