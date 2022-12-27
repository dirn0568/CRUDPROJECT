package com.example.springcrud2.controller;

import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.BoardResponseDto;
import com.example.springcrud2.entity.Board;
import com.example.springcrud2.repository.BoardRepository;
import com.example.springcrud2.service.BoardService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor // 이게 뭘까???
@RequestMapping("/")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index"); //뷰의 이름
        mv.addObject("data", "123");
        return mv; // view + data passvariable
    }
    @ResponseBody
    @GetMapping("/api/posts") // dto로 바꿔라
    public List<BoardResponseDto> boardRead() {
        List<BoardResponseDto> boardResponseDto = boardService.readBoard();
        return boardResponseDto;
    }

    @PostMapping("/api/post") // dto로 바꿔라 Board값을 리턴하면 DB자체를 보내는거라서 과부하 가능성이 높아짐
    public BoardResponseDto boardCreate(@RequestBody BoardRequestDto boardRequestDto) {
        Board board = boardService.createBoard(boardRequestDto);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }

    @PutMapping("/api/post/{id}") // dto로 바꿔라
    public BoardResponseDto boardUpdate(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.updateBoard(id, boardRequestDto);
    }

    @DeleteMapping("/api/post/{id}") // dto로 바꿔라
    public Map boardDelete(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        Map deleteMessage = new HashMap<String, Boolean>();
        deleteMessage = boardService.deleteBoard(id, boardRequestDto);
        return deleteMessage;
    }


    @GetMapping("api/post/{id}")
    public BoardResponseDto boardDetail(@PathVariable Long id) {
        BoardResponseDto boardResponseDto = boardService.detailBoard(id);
        return boardResponseDto;
    }

    /*@GetMapping("api/post/{id}")
    public redirect RedirectView moveDetail() {
        return "redirect:api/post/{id}";
    }*/
}
