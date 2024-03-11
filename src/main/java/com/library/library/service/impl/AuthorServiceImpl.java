package com.library.library.service.impl;

import com.library.library.model.Author;
import com.library.library.repository.AuthorRepository;
import com.library.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void saveAll(List<Author> authors) {
        authorRepository.saveAll(authors);
    }

    @Override
    public boolean isAuthorUnique(Long id, String name) {
        Author author = authorRepository.findByName(name);
        if (author != null)
            return false;
        return true;
    }

    @Override
     public List<Author> findAll(){
        return (List<Author>) authorRepository.findAll();
    }



}
