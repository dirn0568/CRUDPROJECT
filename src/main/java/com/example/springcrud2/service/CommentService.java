package com.example.springcrud2.service;

import com.example.springcrud2.Enum.MemberEnum;
import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.BoardResponseDto;
import com.example.springcrud2.dto.CommentRequestDto;
import com.example.springcrud2.dto.CommentResponseDto;
import com.example.springcrud2.entity.*;
import com.example.springcrud2.jwt.JwtUtil;
import com.example.springcrud2.repository.*;
import com.example.springcrud2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final LikedRepository likedRepository;
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetailsImpl) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, board, userDetailsImpl.getUser());
        commentRepository.save(comment);
        List<Liked> likeds = likedRepository.findAllByComment(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment, Long.valueOf(likeds.size()));
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetailsImpl) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (!comment.getMember().getId().equals(userDetailsImpl.getUser().getId()) && !userDetailsImpl.getUser().getRole().equals(MemberEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        comment.CommentUpdate(commentRequestDto);
        List<Liked> likeds = likedRepository.findAllByComment(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment, Long.valueOf(likeds.size()));
        return commentResponseDto;
    }

    // @Transactional
    public Map deleteComment(Long id, UserDetailsImpl userDetailsImpl) {
        Map deleteMessage = new HashMap<String, String>();

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (!comment.getMember().getId().equals(userDetailsImpl.getUser().getId()) && !userDetailsImpl.getUser().getRole().equals(MemberEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        deleteMessage.put("msg", "댓글 삭제 성공");
        deleteMessage.put("status", 200);
        commentRepository.delete(comment);
        return deleteMessage;
    }

    public void likeComment(@PathVariable Long id, UserDetailsImpl userDetailsImpl) {
        Member member = memberRepository.findById(userDetailsImpl.getUser().getId()).orElseThrow(
                () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지않음")
        );

        if (likedRepository.findByMemberAndComment(member, comment).isPresent()) {
            Liked liked = likedRepository.findByMemberAndComment(member, comment).orElseThrow(
                    () -> new IllegalArgumentException("좋아요가 존재하지않음")
            );
            likedRepository.delete(liked);
        } else {
            Liked liked = new Liked(member, comment);
            likedRepository.save(liked);
        }

//        if (likeCommentRepository.findByMemberAndComment(member, comment).isPresent()) {
//            LikeComment likecomment = likeCommentRepository.findByMemberAndComment(member, comment).orElseThrow(
//                    () -> new IllegalArgumentException("좋아요가 존재하지않음")
//            );
//            likeCommentRepository.delete(likecomment);
//        } else {
//            LikeComment likecomment = new LikeComment(member, comment);
//            likeCommentRepository.save(likecomment);
//        }
    }
}
