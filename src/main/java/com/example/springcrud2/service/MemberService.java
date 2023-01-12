package com.example.springcrud2.service;

import com.example.springcrud2.Enum.MemberEnum;
import com.example.springcrud2.dto.*;
import com.example.springcrud2.entity.Member;
import com.example.springcrud2.jwt.JwtUtil;
import com.example.springcrud2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final String ADMINTOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final PasswordEncoder passwordEncoder;

    //@Transactional
    public MemberResponseDto findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NullPointerException("회원 상세조회 실패")
        );
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return memberResponseDto;
    }

    //@Transactional
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
    public ResponseDto RegisterPost(MemberRequestDto memberRequestDto) { // 여기에 @Valid를 넣으면 안됨???
        //ResponseDto responseDto;

        if (memberRepository.findByName(memberRequestDto.getName()).isPresent()) {
            return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "이미 사용자가 있습니다.");
            //throw new IllegalArgumentException("이미 사용자가 있습니다.");
        }

        MemberEnum memberEnum = MemberEnum.MEMBER;

        if (memberRequestDto.isRole()) {
            if (!memberRequestDto.getAdminToken().equals(ADMINTOKEN)) {
                System.out.println("이거 싱행안함?????");
                return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "관리자 토큰이 다릅니다");
                //throw new IllegalArgumentException("관리자 토큰이 다릅니다.");
            }
            memberEnum = MemberEnum.ADMIN;;
        }

        String username = memberRequestDto.getName();
        String password = passwordEncoder.encode(memberRequestDto.getPw());

        Member member = new Member(username, password, memberEnum);

        memberRepository.save(member);

        //responseDto = new ResponseDto(HttpStatus.OK.value(), "회원가입 성공", null);
        return new ResponseDto(HttpStatus.OK.value(), "회원가입 성공");
    }
    @Transactional
    public ResponseDto LoginPost(MemberRequestDto memberRequestDto, HttpServletResponse response) { // valid 빼는게 좋음
        //Map loginMessage = new HashMap<Integer, Integer>();
        String jwtUtilToken = "";
        String username = memberRequestDto.getName();
        String password = memberRequestDto.getPw();

        Optional<Member> member = memberRepository.findByName(username);

        if (member.isEmpty()) {
            return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "회원을 찾을 수 없습니다"); // 400(프론트), 500(서버) => AOP로 코드 줄이기
        }

        if (!passwordEncoder.matches(password, member.get().getPw())) {
            return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다"); // 400(프론트), 500(서버) => AOP로 코드 줄이기 // 메세지가 다르면 나눠주는게 좋음
        }

        jwtUtilToken = jwtUtil.createToken(member.get().getId());
        response.addHeader("Authorization", jwtUtilToken);

        return new ResponseDto(HttpStatus.OK.value(), "로그인 성공");
    }
}
