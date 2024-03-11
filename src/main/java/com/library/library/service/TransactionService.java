package com.library.library.service;

import com.library.library.model.BorowedItem;

import java.util.List;

public interface TransactionService {
    public void collectReservedBooks(Long[] idList, List<BorowedItem> borowedItemsList);
}
