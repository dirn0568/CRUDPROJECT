package com.example.springcrud2.security;

import com.example.springcrud2.entity.Member;
import com.example.springcrud2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // implements UserDetailsService
public class UserDetailsServiceImpl { //SocialUserDetails, SocialUserDetailsService, UserDetailsService 이걸 안쓰면 어떻게되는거지
    private final MemberRepository memberRepository;
    //@Override
    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new UserDetailsImpl(member);
    }
}