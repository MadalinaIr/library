package com.library.library.service;

import com.library.library.model.Author;

import java.util.List;

public interface AuthorService {
    public List<Author> findAll();
    public void save(Author author);
    public void saveAll(List<Author> authors);
    public boolean isAuthorUnique(Long id, String name);
}
