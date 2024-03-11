package com.library.library.publisher;

import com.library.library.model.Category;
import com.library.library.model.Publisher;
import com.library.library.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class PublisherRepositoryTest {
    @Autowired
    PublisherRepository publisherRepository;

    @Test
    public void testCreatePublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("publisher a-z");
        Publisher savePublisher = publisherRepository.save(publisher);

        assertThat(savePublisher).isNotNull();
        assertThat(savePublisher.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetPublisher(){
        Publisher publisher = publisherRepository.findByName("publisher a-z");
        assertThat(publisher).isNotNull();
    }
}
