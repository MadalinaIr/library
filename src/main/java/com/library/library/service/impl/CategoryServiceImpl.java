package com.library.library.service.impl;


import com.library.library.repository.CategoryRepository;
import com.library.library.model.Category;
import com.library.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        List<Category> categoryList = (List<Category>) categoryRepository.findAll();
        return categoryList;
    }

    public Category findById(Long id){
       // Optional<Category> optinalEntity =  categoryRepository.findById(id);
       // return optinalEntity.get();
        return  categoryRepository.findById(id).get();
    }

    public Category findByName(String name){
        return categoryRepository.findByName(name);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Boolean isCategoryUnique(Long id, String name){
        Category category = categoryRepository.findByName(name);
        if (category != null)
            return false;
        return true;
    }

}
