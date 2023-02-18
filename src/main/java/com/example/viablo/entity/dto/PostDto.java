package com.example.viablo.entity.dto;

import com.example.viablo.entity.Tag;
import com.example.viablo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int cate;
    private String name;
    private String content;
    private int numOfLike;
    private int numOfCmt;
    private String date;
    private int user;
    private List<String> tags;
}
