package com.library.library.service.impl;

import com.library.library.repository.PublisherRepository;
import com.library.library.model.Publisher;
import com.library.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService{
    PublisherRepository publisherRepository;
    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> findAll() {
        return (List<Publisher>)publisherRepository.findAll();
    }

    @Override
    public Publisher findByName(String name) {
        return publisherRepository.findByName(name);
    }

    @Override
    public void save(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    @Override
    public boolean isPublisherUnique(Long id, String name) {
        Publisher publisher = publisherRepository.findByName(name);
        if (publisher != null)
            return true;
        return false;
    }

}
