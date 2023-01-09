package com.example.springcrud2.entity;

import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.CommentRequestDto;
import com.example.springcrud2.dto.CommentResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
// @Setter
@RequiredArgsConstructor // 이게 아닌 그냥 빈 생성자 하나 생성해줘도 가능하긴함
//@NoArgsConstructor // 매개변수가 없는 생성자
//@AllArgsConstructor // 필드 전부 생성자로 들어감
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    @JsonIgnore // 이게 뭔지도 모르겠네
    private Board board;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<LikeComment> LikeComments;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Liked> Likeds;

//    @Column ?? 이거 하나때문에 이게 데이터베이스에 저장이 안됐어 뭐지
//    private Long like;

    //public Comment () {}

    public Comment(CommentRequestDto commentRequestDto, Board board, Member member) {
        this.content = commentRequestDto.getContent();
        this.board = board;
        this.member = member;
    }

    public void CommentUpdate(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
