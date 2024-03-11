package com.library.library.service;

import com.library.library.model.Book;
import com.library.library.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    public static final int PRODUCTS_PER_PAGE = 2;
    Page<Book> findAll(Pageable pageable);
    Book findById(Long id);
    List<Book> findByCategory(Category category);
    List<Book> blurrySearch(String title);
    Page<Book> listByPage(int pageNum);//, String sortField, String sortDir);
   // int pageNum, String sortField, String sortDir){
    void updateBook(Book book);
    void updateBookList(List<Book> booksList);

}
