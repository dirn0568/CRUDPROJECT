package com.example.springcrud2.repository;

import com.example.springcrud2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> { // JpaRepository에 Comment가 들어가는건 알겠는데 Long에는 뭐가 들어가는거지
}
