package com.library.library.service;

import com.library.library.model.Publisher;

import java.util.List;


public interface PublisherService {
    public List<Publisher> findAll();
    public Publisher findByName(String name);
    public void save(Publisher publisher);
    public boolean isPublisherUnique(Long id, String name);
}
