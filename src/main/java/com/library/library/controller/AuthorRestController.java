package com.library.library.controller;

import com.library.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorRestController {
    AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/admin/check_author")
    public String checkDuplicateAuthor(@Param("id") Long id, @Param("author") String author){
        return authorService.isAuthorUnique(id, author)? "Unique": "Duplicated" ;
    }

}
