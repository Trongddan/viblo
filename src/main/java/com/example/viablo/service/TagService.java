package com.example.viablo.service;

import com.example.viablo.entity.Tag;
import com.example.viablo.respository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    @Autowired private TagRepository tagRepository;
    public Optional<Tag> findTagByName(String name){
        return tagRepository.findTagByName(name);
    }
    public void addNewTag(Tag tag){
        tagRepository.save(tag);
    }
}
