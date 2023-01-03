package com.example.springcrud2.repository;

import com.example.springcrud2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String memberName);
    Optional<Member> findByNameAndPw(String memberName, String memberPw);
}
