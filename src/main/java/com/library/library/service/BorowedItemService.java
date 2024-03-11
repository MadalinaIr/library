package com.library.library.service;

import com.library.library.model.BorowedItem;
import org.springframework.data.domain.Page;
import com.library.library.model.security.User;

import java.util.List;

public interface BorowedItemService {
    public static final int PRODUCTS_PER_PAGE = 10;
    List<BorowedItem> findByDueDate(String dueDate);
    List<BorowedItem> findAll();
    public List<BorowedItem> selectCollected(Iterable<Long> idList);
    public List<BorowedItem> findByUserAndReturnedEquals(User user, Boolean returned);
    Page<BorowedItem> listByPageAndUser(int pageNum, String sortField, String sortDir, String keyword, User user);
    Page<BorowedItem> listByPage(int pageNum, String sortField, String sortDir, String keyword);

    void saveBorowedItems(List<BorowedItem> borowedItemsList);
    //  List<BorowedItem> findAll();
}
