package com.example.springcrud2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class LikeComment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // auto로 하면 id값이 혼자서 올라가는데 IDENTITY로 바꾸니 id가 제대로 올라감 IDENTITY도 이상한데?
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    Comment comment;

    public LikeComment(Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
    }
}
