package com.example.viablo.respository;

import com.example.viablo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository <Post,Integer> {
    List<Post> findPostsByUser_IdAndAndCategory_Id(int id,int cateId);
    void deleteById(int id);
    List<Post> findPostByUser_IdAndIdIsNot(int userid,int id);
    List<Post> findPostByTag_Id(int id);
    List<Post> findAllByCategory_IdOrderByNumOfLikeDesc(int cateId);
    List<Post> findAllByNameContains(String name);


}
