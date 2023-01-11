package com.example.springcrud2.entity;

import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.CommentResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

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
    //@JoinColumn //
    private Member member;
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comment = new ArrayList<>();
    @Column
    private String content;
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) // 먼저 지우는 쪽에 cacade 적으면 되나????
    private List<LikeBoard> LikeBoards;
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) // 먼저 지우는 쪽에 cacade 적으면 되나????
    private List<Liked> Likeds;


    //private Liked like1; OneToMany 또는 ManyToOne으로 연결시켜주기


    //@Column
    //@Transient
    //@ElementCollection // 리스트를 연관관계 없이 사용하고 싶을때 쓰는법 //@Transient DB테이블에 없으면 좋아요 기능 불가능!
//    @OneToMany
//    @JoinColumn
//    private List<Member> memberLikes = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto, Member member) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.member = member;
    }

    public void BoardUpdate(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

//    public void BoardLikeAdd(Member member, Board board) { // 좋아요 추가
//        this.memberLikes.add(member);
//        //this.memberLikes.add(member);
//        //board.memberLikes().add(board);
//    }
//    public void BoardLikeRemove(Member member, Board board) { // 좋아요 삭제
//        this.memberLikes.remove(member);
//    }

}
