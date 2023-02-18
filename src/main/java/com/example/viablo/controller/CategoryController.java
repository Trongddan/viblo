package com.example.viablo.controller;

import com.example.viablo.entity.Category;
import com.example.viablo.entity.ObjResponse;
import com.example.viablo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
public class CategoryController {
    @Autowired private CategoryService categoryService;

    @GetMapping("/api/category")
    public ResponseEntity<?> getListCategory(){
        List<Category> categories = categoryService.getListCate();
        if(categories.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","co danh sach",categories));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("fail","khong co danh sach",""));

        }
    }
}
