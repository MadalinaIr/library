package com.library.library.repository;

import com.library.library.model.Book;
import com.library.library.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends  JpaRepository<Book, Long> { //PagingAndSortingRepository<Book, Long> {
//public interface BookRepository extends  PagingAndSortingRepository<Book, Long> {
// JpaRepository interface extends PagingAndSortingRepository which means it has all methods present in the CrudRepository as well

   // @Query("select b from Book b")
  //  List<Book> findAll();
    Page<Book> findAll(Pageable pageable);

    //@Query("SELECT b from Book b WHERE b.id=?1")
    Optional<Book> findById(Long id);
    List<Book> findByCategory(Category category);

    List<Book> findByTitleContaining(String title);
}
