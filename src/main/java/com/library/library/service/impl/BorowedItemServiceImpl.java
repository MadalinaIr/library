package com.library.library.service.impl;

import com.library.library.repository.BorowedItemRepository;
import com.library.library.model.BorowedItem;
import com.library.library.service.BorowedItemService;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.library.library.model.security.User;

@Service
public class BorowedItemServiceImpl implements BorowedItemService {
    BorowedItemRepository borowedItemRepository;

    @Autowired
    public BorowedItemServiceImpl(BorowedItemRepository borowedItemRepository) {
        this.borowedItemRepository = borowedItemRepository;
    }

    public List<BorowedItem> findByUserAndReturnedEquals(User user, Boolean returned){
        return borowedItemRepository.findByUserAndReturnedEquals(user, returned);
    }
    public List<BorowedItem> findByDueDate(String dueDate){
        return borowedItemRepository.findByDueDate(dueDate);
    }

    @Override
    public Page<BorowedItem> listByPage(int pageNum, String sortField, String sortDir, String keyword){
       // test: Sort sort = Sort.by(Sort.Direction.ASC ,sortField);
        if (keyword != null) {
            sortField = "book_title";
        }

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);
        //Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE); //, sort);
        if (keyword != null) {
            return borowedItemRepository.findDistinctByBookTitleLikeOrBookAuthorsNameLike
                                            ("%"+keyword+"%","%"+keyword+"%", pageable);
        }

        return borowedItemRepository.findAll(pageable);
    }

    @Override
    public Page<BorowedItem> listByPageAndUser(int pageNum, String sortField, String sortDir, String keyword, User user){
        // test: Sort sort = Sort.by(Sort.Direction.ASC ,sortField);
        if (keyword != null) {
            sortField = "book_title";
        }

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);
        //Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE); //, sort);
        if (keyword != null) {

            return borowedItemRepository.findAllByUserIdAndBookTitleLikeOrBookAuthorsNameLike
                    (user.getId(),"%"+keyword+"%","%"+keyword+"%", pageable );
        }

        return borowedItemRepository.findAllByUser(pageable, user);

    }

    @Override
    public List<BorowedItem> findAll(){
        return (List<BorowedItem>) borowedItemRepository.findAll();
    }

    @Transactional
    @Override
    public void saveBorowedItems(List<BorowedItem> borowedItemsList){
        try {
            borowedItemRepository.saveAll(borowedItemsList);
        }
        catch (Exception ex) {
            throw ex;
        }
    }
    public List<BorowedItem> selectCollected(Iterable<Long> idList) {
        return (List<BorowedItem>) borowedItemRepository.findAllById(idList);
    }


/*
    @Override
  public Page<BorowedItem> getAll(Pageable pageable) {
       // //  List<Book> bookList = (List<Book>) bookRepository.findAll(pageable);

        Page<BorowedItem> borowedItemsList = borowedItemRepository.findAll(pageable);

        return borowedItemsList;
    }




    public Page<BorowedItem> getAllByKeyword(String keyword, Pageable pageable) {
     //     List<Book> bookList = (List<Book>) borowedItemRepository.findAll(pageable);

       Page<BorowedItem> borowedItemsList = borowedItemRepository.findAll(keyword, pageable);

        return borowedItemsList;
  }
*/
}
