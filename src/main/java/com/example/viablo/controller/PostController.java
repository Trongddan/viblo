package com.example.viablo.controller;

import com.example.viablo.entity.*;
import com.example.viablo.entity.dto.PostDto;
import com.example.viablo.respository.PostRepository;
import com.example.viablo.respository.UserRespository;
import com.example.viablo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@CrossOrigin("http://localhost:3000/")
@RestController
public class PostController {
    @Autowired private PostService postService;
    @Autowired private TagService tagService;
    @Autowired private UserService userService;
    @Autowired private PostRepository postRepository;
    @Autowired private CommentService commentService;
    @Autowired private CategoryService categoryService;
    @PostMapping("/api/addnewPost")
    public ResponseEntity<?> addNewPost(@RequestBody PostDto postDto){
        Post post = new Post();
        post.setName(postDto.getName());
        post.setContent(postDto.getContent());
        post.setDate(new Date().toString());
        post.setNumOfCmt(0);
        post.setNumOfLike(0);
        Optional<Category> categoryOptional = categoryService.getCateById(postDto.getCate());
        if(categoryOptional.isPresent()){
             post.setCategory(categoryOptional.get());
        }
        Optional<User> userOptional = userService.getUserById(postDto.getUser());
        System.out.println(userOptional);
        post.setUser(userOptional.get());

        for(String tag : postDto.getTags()){
            Optional<Tag> tagOptional = tagService.findTagByName(tag);
            if(tagOptional.isPresent()){
                post.addNewTag(tagOptional.get());
            }else {
                Tag tag1 = new Tag();
                tag1.setName(tag);
                tagService.addNewTag(tag1);
                post.addNewTag(tag1);

            }
        }

        postService.AddNewPost(post);

        return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","Them thanh cong",""));
    }
//lay danh sach bai viet/cau hoi
    @GetMapping("/api/listpost/{id}")
    public ResponseEntity<?> getListPost(@PathVariable int id){
        List<Post> postList = postService.getListPost2(id);
        if(postList.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail"," co danh sach",postList));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail","khong co danh sach",""));
        }
    }


// xem chi tiet bai viet
    @GetMapping("/api/post/{id}")
    public ResponseEntity<?> getPostId(@PathVariable int id){
        Post post = postService.getPostById(id);
        if(post !=null){
            return ResponseEntity.ok().body(new ObjResponse("ok","co bai viet",post));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjResponse("fail","khong co bai viet",""));
        }
    }
 //lay post theo user
    @GetMapping("/api/post/u/{id}/c/{cate}")
    public ResponseEntity<?> getPostByUser(@PathVariable int id, @PathVariable int cate){

        List<Post> posts = postService.getListPostByUser(id,cate);
        if(posts !=null){
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","co bai viet",posts));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail","khong co bai viet",""));

        }
    }
    @DeleteMapping("/api/post/d/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable int id){
        postService.deletePostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","xoa bai viet",""));
    }

//    @GetMapping("/api/post/u/ex/{id}")
//    public ResponseEntity<?> getpostExceptionId(@PathVariable int id){
//        List<Post> post = postRepository.findPostByIdIsNot(id);
//        if(post.size()>0){
//            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","xoa bai viet",post));
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","xoa bai viet",""));
//
//    }

        @GetMapping("/api/post/tag/{id}")
    public ResponseEntity<?> getpostExceptionId(@PathVariable int id){
        List<Post> post = postRepository.findPostByTag_Id(id);
        if(post.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","Co bai viet",post));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail"," khong co bai viet",""));
    }
    // update lượt xem cho bài viết
    @PatchMapping("api/post/{id}")
    public ResponseEntity<?> updateView(@PathVariable int id){
        Post post = postService.getPostById(id);
        int view = post.getNumOfLike();
        if(post !=null){
        post.setId(id);
        post.setNumOfLike(view+1);
        postService.AddNewPost(post);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","update thanh cong",""));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail","update that bai",""));
        }
    }

    //count cmt by id
    @PatchMapping ("/api/countcmt/{id}")
    public ResponseEntity<?> CountCmt (@PathVariable int id,@RequestParam("n") int n){
//        int cmt = commentService.countCmt(id);
//        System.out.println("alo" +cmt);
        Post post = postService.getPostById(id);
        int slCmt = post.getNumOfCmt();
        if(post!=null){
            post.setNumOfCmt(slCmt+n);
            postService.AddNewPost(post);
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","update thanh cong",""));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail","update that bai",""));
        }

    }

    @GetMapping("/api/search/")
    public ResponseEntity<?> SearchPost(@RequestParam("p") String p){
        if(p ==""){
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail","khong co danh sach",""));
        }else{
            List<Post> postList = postRepository.findAllByNameContains(p);
            if(postList.size()>0){
                return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","co danh sach",postList));
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail","khong co danh sach",""));
            }
        }

    }


}
