package com.example.springcrud2.service;

import com.example.springcrud2.Enum.MemberEnum;
import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.BoardResponseDto;
import com.example.springcrud2.dto.CommentRequestDto;
import com.example.springcrud2.dto.CommentResponseDto;
import com.example.springcrud2.entity.Board;
import com.example.springcrud2.entity.Comment;
import com.example.springcrud2.entity.Member;
import com.example.springcrud2.jwt.JwtUtil;
import com.example.springcrud2.repository.BoardRepository;
import com.example.springcrud2.repository.CommentRepository;
import com.example.springcrud2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        if (!jwtUtil.vaildation(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        Member member = memberRepository.findById(Long.parseLong(jwtUtil.getUserInfoFromToken(token).getSubject())).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, board, member);
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        if (!jwtUtil.vaildation(token)) {
            throw new IllegalArgumentException("올바른 아이디가 아닙니다.");
        }
        Member member = memberRepository.findById(Long.parseLong(jwtUtil.getUserInfoFromToken(token).getSubject())).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (!comment.getMember().getId().equals(member.getId()) && !member.getRole().equals(MemberEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        comment.CommentUpdate(commentRequestDto);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    // @Transactional
    public Map deleteComment(Long id, HttpServletRequest request) {
        Map deleteMessage = new HashMap<String, String>();
        String token = JwtUtil.getToken(request);
        if (!jwtUtil.vaildation(token)) {
            throw new IllegalArgumentException("올바른 아이디가 아닙니다.");
        }
        Member member = memberRepository.findById(Long.parseLong(jwtUtil.getUserInfoFromToken(token).getSubject())).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (!comment.getMember().getId().equals(member.getId()) && !member.getRole().equals(MemberEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        deleteMessage.put("msg", "댓글 삭제 성공");
        deleteMessage.put("status", 200);
        commentRepository.delete(comment);
        return deleteMessage;
    }


}
