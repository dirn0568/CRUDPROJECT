package com.example.springcrud2.repository;

import com.example.springcrud2.entity.Board;
import com.example.springcrud2.entity.LikeBoard;
import com.example.springcrud2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    Optional<LikeBoard> findByMemberAndBoard(Member member, Board board);
    List<LikeBoard> findAllByBoard(Board board);
}
