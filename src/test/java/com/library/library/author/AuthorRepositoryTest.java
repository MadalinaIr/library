package com.library.library.author;

import com.library.library.model.Author;
import com.library.library.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testCreateAuthor(){
        Author author = new Author();
        author.setName("Lucian Blaga");
        author.setDescription(" ");
        Author savedAuthor = authorRepository.save(author);

        assertThat(savedAuthor.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAuthor(){
        Author author = authorRepository.findByName("Lucian Blaga");
        assertThat(author).isNotNull();
    }



}
