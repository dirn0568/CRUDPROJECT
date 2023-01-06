package com.example.springcrud2.entity;

import com.example.springcrud2.dto.BoardRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@ToString(exclude = "Comment")
@Entity
@Getter
//@Setter
//@EqualsAndHashCode(exclude = {"board"})
@RequiredArgsConstructor // 이게 뭐지??? 생성자를 처리해주는거 같은데 잘 모르겠음
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @ManyToOne
    private Member member;
    @OneToMany(mappedBy = "board")
    @JsonIgnore
    List<Comment> comment = new ArrayList<>();
    @Column
    private String content;

    public Board(BoardRequestDto boardRequestDto, Member member) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.member = member;
    }

    public void BoardUpdate(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }
}
