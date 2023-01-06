package com.example.springcrud2.service;

import com.example.springcrud2.Enum.MemberEnum;
import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.BoardResponseDto;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.*;


@Service
@RequiredArgsConstructor // 이거는 또 뭘까
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional // 이거를 빼면 데이터 생성이 안되나????
    public Board createBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        Board board = new Board();
        if (jwtUtil.vaildation(token)) {
            Optional<Member> member = memberRepository.findById(Long.parseLong(jwtUtil.getUserInfoFromToken(token).getSubject()));
            board = new Board(boardRequestDto, member.get());
            boardRepository.save(board);
        }
        return board;
    }

    @Transactional // List<Board> -> List로 바꿔서 리턴해보기
    public List<BoardResponseDto> readBoard() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtAsc(); // 복수형
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boards) {
            BoardResponseDto tempboardResponseDto = new BoardResponseDto(board);
            boardResponseDto.add(tempboardResponseDto);
        }
        return boardResponseDto;
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        if (!jwtUtil.vaildation(token)) {
            throw new IllegalArgumentException("올바른 아이디가 아닙니다.");
        }
        Optional<Member> member = memberRepository.findById(Long.parseLong(jwtUtil.getUserInfoFromToken(token).getSubject()));
        Optional<Board> board = boardRepository.findById(id);
        if (!board.get().getMember().equals(member.get()) && !member.get().getRole().equals(MemberEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        board.get().BoardUpdate(boardRequestDto);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board.get());
        return boardResponseDto;
    }

    public Map deleteBoard(Long id, HttpServletRequest request) {
        Map deleteMessage = new HashMap<Integer, Integer>();
        String token = JwtUtil.getToken(request);
        if (!jwtUtil.vaildation(token)) {
            throw new IllegalArgumentException("올바른 아이디가 아닙니다.");
        }
        Optional<Member> member = memberRepository.findById(Long.parseLong(jwtUtil.getUserInfoFromToken(token).getSubject()));
        Optional<Board> board = boardRepository.findById(id);
        if (!board.get().getMember().equals(member.get()) && !member.get().getRole().equals(MemberEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        deleteMessage.put("msg", "게시물 삭제 성공");
        deleteMessage.put("status", 200);
        boardRepository.delete(board.get());
        return deleteMessage;
    }

    @Transactional // List<Board> -> List로 바꿔서 리턴해보기
    public BoardResponseDto detailBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지않음")
        );
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }
}
