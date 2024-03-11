package com.library.library.service;

import com.library.library.model.ReservedItem;
import com.library.library.model.security.User;

import java.util.List;

public interface ReservedItemService {
    public List<ReservedItem> getAllReservedItems();
    public void updateReservedItem(ReservedItem reservedItem);
    public ReservedItem findById(Long id);
    public void deleteById(Long id);
    //List<ReservedItem> findAll();
    public List<ReservedItem> findByUser(User user);
    public List<ReservedItem> selectCollected(Iterable<Long> idList);
    public void deleteCollected(List<Long> idList);

}
