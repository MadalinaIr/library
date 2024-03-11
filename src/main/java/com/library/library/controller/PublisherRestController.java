package com.library.library.controller;

import com.library.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherRestController {
    private PublisherService publisherService;

    @Autowired
    public PublisherRestController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/admin/check_publisher")
    public String checkDuplicatePublisher(@Param("id") Long id, @Param("publisher") String publisher){
        return publisherService.isPublisherUnique(id, publisher)? "Unique" : "Duplicated";
    }
}
