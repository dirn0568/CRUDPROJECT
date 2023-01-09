package com.example.springcrud2.repository;

import com.example.springcrud2.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Optional<LikeComment> findByMemberAndComment(Member member, Comment comment);
    List<LikeComment> findAllByComment(Comment comment);
}
