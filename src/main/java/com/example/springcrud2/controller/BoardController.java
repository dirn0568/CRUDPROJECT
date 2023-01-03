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

import javax.servlet.http.HttpServletRequest;

@RestController // @ResponseBody + @Controller
@RequiredArgsConstructor // final로 선언된거를 알아서 위치를 찾아줌(근데 생성자를 따로 만들어주는건가???)
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

    @GetMapping("/api/posts")
    public List<BoardResponseDto> boardRead() {
        List<BoardResponseDto> boardResponseDto = boardService.readBoard();
        return boardResponseDto;
    }

    @PostMapping("/api/post")
    public BoardResponseDto boardCreate(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Board board = boardService.createBoard(boardRequestDto, request);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }

    @PutMapping("/api/post/{id}")
    public BoardResponseDto boardUpdate(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.updateBoard(id, boardRequestDto, request);
    }

    @DeleteMapping("/api/post/{id}") // dto로 바꿔라
    public Map boardDelete(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }


    @GetMapping("api/post/{id}")
    public BoardResponseDto boardDetail(@PathVariable Long id) {
        BoardResponseDto boardResponseDto = boardService.detailBoard(id);
        return boardResponseDto;
    }

//    @GetMapping("api/post/{id}")
//    public ModelAndView boardDetail(@PathVariable Long id) {
//        ModelAndView mv = new ModelAndView("detail2");
//        Board board = new Board();
//        board.setTitle("너를떠올리지않게");
//        //List<Board> boards = new ArrayList<>();
//        //boards.add(board);
//        Board board2 = new Board();
//        board2.setTitle("떨어지는날 잡아줘");
//        //boards.add(board2);
//        Board board3 = new Board();
//        board3.setTitle("헬로오오오오오옹");
//        //boards.add(board3);
//        mv.addObject("title", boards);
//        mv.addObject("title1", boards.get(1));
//        mv.addObject("title2", boards.get(2));
//        mv.addObject("twotwo", "two");
//        return mv;
//    }
}
