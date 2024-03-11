package com.library.library.service.impl;

import com.library.library.repository.ReservedItemRepository;
import com.library.library.model.security.User;
import com.library.library.model.Book;
import com.library.library.model.ReservedItem;
import com.library.library.repository.BookRepository;
import com.library.library.service.ReservedItemService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservedItemServiceImpl implements ReservedItemService {
    ReservedItemRepository reservedItemRepository;

    BookRepository bookRepository;

    @Autowired
    public ReservedItemServiceImpl(ReservedItemRepository reservedItemRepository,
                                   BookRepository bookRepository) {
        this.reservedItemRepository = reservedItemRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<ReservedItem> getAllReservedItems() {
        return (List<ReservedItem>) reservedItemRepository.findAll(); //.get();
    }

    @Override
    public void updateReservedItem(ReservedItem reservedItem) {
        Book book = bookRepository.findById(reservedItem.getBook().getId()).get();
        if (book.getInStockNumber() > 0) {
            book.setInStockNumber(book.getInStockNumber() - 1);
            bookRepository.save(book);
            reservedItemRepository.save(reservedItem);
        }

    }

    @Override
    public ReservedItem findById(Long id) {
        return reservedItemRepository.findById(id).get();
    }

    public List<ReservedItem> findByUser(User user) {
        return reservedItemRepository.findByUser(user);
    }

    @Transactional
    @Override
    public void deleteCollected(List<Long> idList) {
        reservedItemRepository.deleteCollected(idList);
    }

    @Override
    public void deleteById(Long id) {
        reservedItemRepository.deleteById(id);
    }

    @Override
    public List<ReservedItem> selectCollected(Iterable<Long> idList) {
        return (List<ReservedItem>) reservedItemRepository.findAllById(idList);
    }


}
