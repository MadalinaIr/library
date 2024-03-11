package com.library.library.service.impl;

import com.library.library.model.Book;
import com.library.library.model.Category;
import com.library.library.repository.BookRepository;
import com.library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> findAll(Pageable pageable) {
      //  List<Book> bookList = (List<Book>) bookRepository.findAll(pageable);

        Page<Book> bookList = bookRepository.findAll(pageable);
        /*
        List<Book> activeBookList = new ArrayList<>();

        for (Book book: bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
        */
        return bookList;
    }
    /*
    public Book findOne(Long id) {
        return bookRepository.findOne(id);
    }
 */
    public Book findById(Long id) {
        //return bookRepository.findOne(id);
        Optional<Book> optinalEntity =  bookRepository.findById(id);
        return optinalEntity.get();

      //  return bookRepository.findById(id);

    }

    @Override
    public List<Book> findByCategory(Category category) {
        return bookRepository.findByCategory(category);
        //return null;
    }
/*
    @Override
    public List<Book> findByTitleContaining(String title) {
        return bookRepository.findByTitleContaining(title);
    }
*/
    public List<Book> blurrySearch(String title) {
        List<Book> bookList = (List<Book>) bookRepository.findByTitleContaining(title);
        return bookList;
    }

    public Page<Book> listByPage(int pageNum){//, String sortField, String sortDir){
       /* Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        */
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);//, sort);
        return bookRepository.findAll(pageable);
    }

    public void updateBook(Book book){
        bookRepository.save(book);
    }
    public void updateBookList(List<Book> booksList){
        bookRepository.saveAll(booksList);
    }

}
