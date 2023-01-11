package com.example.springcrud2.controller;

import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.BoardResponseDto;
import com.example.springcrud2.dto.CommentRequestDto;
import com.example.springcrud2.dto.CommentResponseDto;
import com.example.springcrud2.entity.Board;
import com.example.springcrud2.entity.Comment;
import com.example.springcrud2.entity.Member;
import com.example.springcrud2.jwt.JwtUtil;
import com.example.springcrud2.security.UserDetailsImpl;
import com.example.springcrud2.service.BoardService;
import com.example.springcrud2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor // requestmapping 공통되는거 빼주기
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/api/comment/{id}")
    public CommentResponseDto commentCreate(@PathVariable Long id,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        CommentResponseDto commentResponseDto = commentService.createComment(id, commentRequestDto, userDetailsImpl);
        return commentResponseDto;
    }

    @PutMapping("api/comment/{id}")
    public CommentResponseDto commentUpdate(@PathVariable Long id,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        CommentResponseDto commentResponseDto = commentService.updateComment(id, commentRequestDto, userDetailsImpl);
        return commentResponseDto;
    }

    @DeleteMapping("api/comment/{id}")
    public Map<String, String> commentDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.deleteComment(id, userDetailsImpl);
    }

    @PostMapping("api/commentlike/{id}") // -,/
    public void commentLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        commentService.likeComment(id, userDetailsImpl);
    }
}
