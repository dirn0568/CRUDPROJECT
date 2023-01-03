package com.example.springcrud2.service;

import com.example.springcrud2.dto.LoginDto;
import com.example.springcrud2.dto.MemberResponseDto;
import com.example.springcrud2.dto.RegisterDto;
import com.example.springcrud2.entity.Member;
import com.example.springcrud2.jwt.JwtUtil;
import com.example.springcrud2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil; // 객체를 새로 생성하지않고 이걸 하면 사용이되네 뭐지???

    @Transactional
    public MemberResponseDto findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NullPointerException("회원 상세조회 실패")
        );
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return memberResponseDto;
    }

    @Transactional
    public List<MemberResponseDto> findAllMember() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for (Member member : members) {
            MemberResponseDto memberResponseDto = new MemberResponseDto(member);
            memberResponseDtos.add(memberResponseDto);
        }
        return memberResponseDtos;
    }



    @Transactional
    // @ExceptionHandler 안쓰는거
    public Map RegisterPost(@Valid Member member) {
        Map registerMessage = new HashMap<Integer, Integer>(); // 뭐지 Intger, Integer도 됨??? 문자형 넣었는데????
        if (!memberRepository.findByName(member.getName()).isPresent()) {
            memberRepository.save(member);
        }
        registerMessage.put("msg", "회원가입 성공");
        registerMessage.put("status", 200);
        return registerMessage;
    }
    @Transactional
    public Map LoginPost(Member member, HttpServletResponse response) {
        Map loginMessage = new HashMap<Integer, Integer>();
        String jwtUtilToken = "";
        if (memberRepository.findByNameAndPw(member.getName(), member.getPw()).isPresent()) {
            Optional<Member> member1 = memberRepository.findByNameAndPw(member.getName(), member.getPw());
            jwtUtilToken = jwtUtil.createToken(member1.get().getId());
            loginMessage.put("msg", "로그인 성공");
            loginMessage.put("status", 200);
            response.addHeader("Authorization", jwtUtilToken);
            return loginMessage;
        }
        return loginMessage;
    }
}
