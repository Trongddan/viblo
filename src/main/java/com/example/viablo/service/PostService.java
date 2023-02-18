package com.example.viablo.service;

import com.example.viablo.entity.Post;
import com.example.viablo.respository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired private PostRepository postRepository;
    public void AddNewPost(Post post){
        postRepository.save(post);
    }
    public List<Post> getListPost(){
        return postRepository.findAll();
    }
    public Post getPostById(int id){
        Optional<Post> postOptional= postRepository.findById(id);
        if(postOptional.isPresent()){
            return postOptional.get();
        }{
            return null;
        }
    }
    public List<Post> getListPostByUser(int id,int cateId){
        List<Post> postList = postRepository.findPostsByUser_IdAndAndCategory_Id(id,cateId);
        if(postList.size()>0){
            return postList;
        }else {
            return null;
        }
    }

    public void deletePostById(int id){
        postRepository.deleteById(id);
    }
    public List<Post> getListPost2(int id){
        return postRepository.findAllByCategory_IdOrderByNumOfLikeDesc(id);
    }

}
