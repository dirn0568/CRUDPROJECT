package com.example.springcrud2.security;

import com.example.springcrud2.entity.Member;
import com.example.springcrud2.Enum.MemberEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    public Member getUser() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // ArrayList도 될꺼같은데?
        MemberEnum role = member.getRole();
        String authority = String.valueOf(role); // role.MEMBER

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public String getUsername() {
        System.out.println("20230108실행이 되는거임2222222222222");
        return null;
    }

    @Override
    public String getPassword() {
        System.out.println("20230108실행이 되는거임333333333333333");
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        System.out.println("20230108실행이 되는거임4444444444444");
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        System.out.println("20230108실행이 되는거임55555555555555");
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        System.out.println("20230108실행이 되는거임6666666666666666");
        return false;
    }

    @Override
    public boolean isEnabled() {
        System.out.println("20230108실행이 되는거임777777777777777");
        return false;
    }
}