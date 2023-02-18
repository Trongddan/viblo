package com.example.viablo.service;

import com.example.viablo.entity.Comment;
import com.example.viablo.respository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired private CommentRepository commentRepository;
    public void addNewCmt(Comment comment){
        commentRepository.save(comment);
    }
    public List<Comment> getCmtByPost(int id){
        return commentRepository.findCommentByPost_Id(id);
    }
    public Comment getCmtById(int id){
        Optional<Comment> comment= commentRepository.findById(id);
        if(comment.isPresent()){
            return comment.get();
        }
        return null;
    }
    public void deleteCmt(int id){
        commentRepository.deleteById(id);
    }
    public int countCmt(int id){
        return commentRepository.countAllByPost_id(id);
    }
}
