package com.library.library.service.impl;

import com.library.library.model.BorowedItem;
import com.library.library.service.BookService;
import com.library.library.service.TransactionService;
import com.library.library.service.BorowedItemService;
import com.library.library.service.ReservedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    ReservedItemService reservedItemService;
    BorowedItemService borowedItemService;

    BookService bookService;

    @Autowired
    public TransactionServiceImpl(ReservedItemService reservedItemService,
                                  BorowedItemService borowedItemService,
                                  BookService bookService) {
        this.reservedItemService = reservedItemService;
        this.borowedItemService = borowedItemService;
        this.bookService = bookService;
    }
    @Transactional(rollbackFor = Exception.class)
    public void collectReservedBooks(Long[] idList, List<BorowedItem> borowedItemsList) {
        try {
            //Step 1: move reserved items from table ReservedItems to BorowedItems
            borowedItemService.saveBorowedItems(borowedItemsList);
            //Step2: delete the items from ReservedItems, once the items are moved to the BorowedTable
            reservedItemService.deleteCollected(List.of(idList));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
