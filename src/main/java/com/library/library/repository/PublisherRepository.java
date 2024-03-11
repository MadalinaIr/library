package com.library.library.repository;

import com.library.library.model.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    public Publisher findByName(String name);
}
