package com.library.library.service;

import com.library.library.model.Category;

import java.util.List;


public interface CategoryService{
    List<Category> findAll();
    Category findById(Long id);
    Category findByName(String name);
    public void save(Category category) throws Exception;
    public Boolean isCategoryUnique(Long id, String name);
}
