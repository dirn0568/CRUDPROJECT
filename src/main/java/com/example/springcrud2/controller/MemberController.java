package com.example.springcrud2.controller;

import com.example.springcrud2.dto.MemberResponseDto;
import com.example.springcrud2.dto.RegisterDto;
import com.example.springcrud2.entity.Member;
import com.example.springcrud2.jwt.JwtUtil;
import com.example.springcrud2.service.MemberService;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import com.example.springcrud2.dto.LoginDto;

import javax.servlet.http.HttpServletResponse;

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
    @PostMapping("/api/register")
    public Map RegisterPost(@RequestBody Member member) {
        Map registerMessage = memberService.RegisterPost(member);
        return registerMessage;
    }
    @PostMapping("/api/login")
    public Map LoginPost(@RequestBody Member member, HttpServletResponse response) {
        Map response1 = memberService.LoginPost(member, response);
        return response1;
    }
}
