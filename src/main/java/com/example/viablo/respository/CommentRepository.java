package com.example.viablo.respository;

import com.example.viablo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findCommentByPost_Id(int id);
    int countAllByPost_id(int id);
}
