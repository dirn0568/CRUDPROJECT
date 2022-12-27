package com.example.springcrud2.service;

import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.BoardResponseDto;
import com.example.springcrud2.entity.Board;
import com.example.springcrud2.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.*;


@Service
@RequiredArgsConstructor // 이거는 또 뭘까
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional // 이거를 빼면 데이터 생성이 안되나????
    public Board createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        boardRepository.save(board);
        return board;
    }

    @Transactional // List<Board> -> List로 바꿔서 리턴해보기
    public List<BoardResponseDto> readBoard() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc(); // 복수형
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boards) {
            BoardResponseDto tempboardResponseDto = new BoardResponseDto(board);
            boardResponseDto.add(tempboardResponseDto);
        }
        return boardResponseDto;
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
        System.out.println("어디서 막힌거지2");
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않음")
        ); // Optional로 묶으면 사용 불가능! <- Optioanl 객체로 한번 묶어 줬기때문에 return 하거나 받을때 Optional 객체로 받거나 리턴해야함
        if (board.getPassword().equals(boardRequestDto.getPassword())) {
            System.out.println("같은지 확인완료");
            board.BoardUpdate(boardRequestDto);
        }
        System.out.println("같은지 확인안함");
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }

    public Map deleteBoard(Long id, BoardRequestDto boardRequestDto) {
        Map deleteMessage = new HashMap<String, Boolean>();
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않음")
        );
        if (board.getPassword().equals(boardRequestDto.getPassword())) {
            boardRepository.delete(board);
            deleteMessage.put("success", true);
        } else {
            deleteMessage.put("fail", false);
        }
        return deleteMessage;
    }

    @Transactional // List<Board> -> List로 바꿔서 리턴해보기
    public BoardResponseDto detailBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않음")
        );
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }
}
