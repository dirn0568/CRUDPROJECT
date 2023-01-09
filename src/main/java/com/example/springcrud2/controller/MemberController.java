package com.example.springcrud2.controller;

import com.example.springcrud2.dto.*;
import com.example.springcrud2.entity.Member;
import com.example.springcrud2.jwt.JwtUtil;
import com.example.springcrud2.service.MemberService;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor // 어노테이션 알아보기 final붙어있는거 이어준다 생성자를 만들어준다
public class MemberController {
    //@Autowired // spring di 생성자가 필요했는데 @RequiredArgsConstructor, final 이거를 통해서 생성했었음 이거를 사용하기 싫으면 @Autowired 작성
    private final MemberService memberService; // final을 쓴다는건 무조건 사용을 하겠다는것을 알려주는 것이다
                                                // 이게 없으면 사용을 안해도 된다고 프로그램이 생각한다
    @GetMapping("/member/{id}")
    public MemberResponseDto getMemberInfo(@PathVariable Long id) {
        MemberResponseDto memberResponseDto = memberService.findMember(id);
        return memberResponseDto;
    }
    @GetMapping("/member")
    public List<MemberResponseDto> getMemberList() {
        List<MemberResponseDto> memberResponseDto = memberService.findAllMember();
        return memberResponseDto;
    }

    //    public MemberController(MemberService memberService) {
    //        this.memberService = memberService;
    //    }

    @GetMapping("/register")
    public ModelAndView RegisterGet() {
        ModelAndView mv = new ModelAndView("register");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView LoginGet() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @PostMapping("/api/register")
    public ResponseDto RegisterPost(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        ResponseDto responseDto = memberService.RegisterPost(memberRequestDto);
        return responseDto;
    }

    @PostMapping("/api/login")
    public ResponseDto LoginPost(@RequestBody @Valid MemberRequestDto memberRequestDto, HttpServletResponse response) {
        System.out.println("*********************************************************");
        System.out.println("UserController.login");
        System.out.println("userDetails.getUsername() = " + memberRequestDto.getName());
        System.out.println("userDetails.getUsername() = " + memberRequestDto.getPw());
        System.out.println("*********************************************************");
        ResponseDto responseDto = memberService.LoginPost(memberRequestDto, response);
        return responseDto;
    }

    @GetMapping("/api/user/forbidden")
    public ModelAndView Forbidden() {
        ModelAndView mv = new ModelAndView("register");
        mv.setViewName("forbidden");
        return mv;
    }
}
