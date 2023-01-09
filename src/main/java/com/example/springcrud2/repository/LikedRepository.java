package com.example.springcrud2.repository;

import com.example.springcrud2.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByMemberAndBoard(Member member, Board board);
    List<Liked> findAllByBoard(Board board);
    Optional<Liked> findByMemberAndComment(Member member, Comment comment);
    List<Liked> findAllByComment(Comment comment);
}
