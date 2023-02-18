package com.example.viablo.controller;

import com.example.viablo.entity.Comment;
import com.example.viablo.entity.ObjResponse;
import com.example.viablo.entity.Post;
import com.example.viablo.entity.User;
import com.example.viablo.entity.dto.CommentDto;
import com.example.viablo.service.CommentService;
import com.example.viablo.service.PostService;
import com.example.viablo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000/")
@RestController
public class CommentController {
    @Autowired private PostService postService;
    @Autowired private CommentService commentService;
    @Autowired private UserService userService;
    @PostMapping("/api/addcomment")
    public ResponseEntity<?> addNewCmt(@RequestBody CommentDto commentDto){
        Comment comment = new Comment();
        //lay post va user :
        Post post = postService.getPostById(commentDto.getPostId());
        User user = userService.getUserById(commentDto.getUserId()).get();
        //tien hanh them:
        comment.setContent(commentDto.getContent());
        comment.setPost(post);
        comment.setUser(user);
        commentService.addNewCmt(comment);
        return ResponseEntity.ok().body(new ObjResponse("ok","them thanh cong",""));
    }

    @GetMapping("/api/getcmt/p/{id}")
    public ResponseEntity<?> getCmtByPost(@PathVariable int id){
        List<Comment> comments = commentService.getCmtByPost(id);
        if(comments.size()>0){
            return ResponseEntity.ok().body(new ObjResponse("ok","co danh sach",comments));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail","Khong co danh sach",""));

        }
    }

    @DeleteMapping("/api/deletecmt/")
    public  ResponseEntity<?> deleteCmtByPost(@RequestParam("cmt_id") int cmtId,@RequestParam("user_id") int userId){
        Comment comment = commentService.getCmtById(cmtId);
        if(comment!=null){
        if(comment.getUser().getId()==userId){
            commentService.deleteCmt(cmtId);
            return ResponseEntity.ok().body(new ObjResponse("ok","Xoa thanh cong",""));
        }else {
            return ResponseEntity.ok().body(new ObjResponse("fail","Xoa that bai",""));

        }
        }else {
            return ResponseEntity.ok().body(new ObjResponse("fail","Xoa that bai",""));
        }
    }


}
