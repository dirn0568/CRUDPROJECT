package com.example.springcrud2.dto;

import com.example.springcrud2.entity.Board;
import com.example.springcrud2.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long id;
    private String title;
    private String content;
    private String memberName;

    public BoardResponseDto(Board board) {
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.memberName = board.getMember().getName();
    }
}
