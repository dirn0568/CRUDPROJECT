package com.example.springcrud2.entity;

import com.example.springcrud2.dto.BoardRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor // 이게 뭐지??? 생성자를 처리해주는거 같은데 잘 모르겠음
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column
    private String password;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String content;

    public Board(BoardRequestDto boardRequestDto) {
        this.password = boardRequestDto.getPassword();
        this.title = boardRequestDto.getTitle();
        this.author = boardRequestDto.getAuthor();
        this.content = boardRequestDto.getContent();
    }

    public void BoardUpdate(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.author = boardRequestDto.getAuthor();
        this.content = boardRequestDto.getContent();
    }
}
