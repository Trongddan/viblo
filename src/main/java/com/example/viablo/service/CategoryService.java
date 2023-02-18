package com.example.viablo.service;

import com.example.viablo.entity.Category;
import com.example.viablo.respository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {
    @Autowired private CategoryRepository categoryRepository;
    public Optional<Category>  getCateById(int id){
        return categoryRepository.findById(id);
    }
    public List<Category> getListCate(){
        return categoryRepository.findAll();
    }
}
