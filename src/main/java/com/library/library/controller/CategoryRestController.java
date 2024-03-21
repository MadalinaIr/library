package com.library.library.controller;

import com.library.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {
    CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/check_category")
    public String checkDuplicateCategory(@Param("id") Long id, @Param("category") String category){
        return categoryService.isCategoryUnique(id, category)? "Unique" : "Duplicated";
    }



}
