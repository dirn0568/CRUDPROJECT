package com.example.springcrud2.dto;

import com.example.springcrud2.entity.Board;
import com.example.springcrud2.entity.Comment;
import com.example.springcrud2.entity.LikeBoard;
import com.example.springcrud2.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long id;
    private String title;
    private String content;
    private String memberName;
    private Long like;
    private List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();


//    private Integer resultCode;
//    private String message;


    public BoardResponseDto(Board board, Long boardLikeCount) {
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.memberName = board.getMember().getName();
        this.like = boardLikeCount;
        for (Comment comment : board.getComment()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment, Long.valueOf(comment.getLikeds().size()));
            this.commentResponseDtoList.add(commentResponseDto);
        }
    }

//    public void BoardErrorResponseDto(Integer resultCode, String message) {
//        this.resultCode = resultCode;
//        this.message = message;
//    }
}
