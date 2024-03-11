package com.library.library.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70, nullable = false, unique = true)
    private String title;
    //@ManyToMany(mappedBy = "books", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private List<Author> authors;
    @OneToOne
    private Publisher publisher;
    @OneToOne
    private Category category;
    private String publicationYear;
    private String language;

    private int numberOfPages;
    private int isbn;

    @Column(columnDefinition="text")
    private String description;
    private int inStockNumber;

    @Transient
    private MultipartFile bookImage;

    public Book() {
    }

    public Book(String title, List<Author> authors,
                Publisher publisher, Category category,
                String publicationYear, String language,
                int numberOfPages,
                int isbn, String description,
                int inStockNumber, MultipartFile bookImage) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.category = category;
        this.publicationYear = publicationYear;
        this.language = language;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.description = description;
        this.inStockNumber = inStockNumber;
        this.bookImage = bookImage;
    }

    public Book(Long id, String title, List<Author> authors, Publisher publisher,
                Category category, String publicationYear, String language, int numberOfPages, int isbn,
                String description, int inStockNumber, MultipartFile bookImage) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.category = category;
        this.publicationYear = publicationYear;
        this.language = language;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.description = description;
        this.inStockNumber = inStockNumber;
        this.bookImage = bookImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInStockNumber() {
        return inStockNumber;
    }

    public void setInStockNumber(int inStockNumber) {
        this.inStockNumber = inStockNumber;
    }

    public MultipartFile getBookImage() {
        return bookImage;
    }

    public void setBookImage(MultipartFile bookImage) {
        this.bookImage = bookImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", publisher=" + publisher +
                ", category=" + category +
                ", publicationYear='" + publicationYear + '\'' +
                ", language='" + language + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", isbn=" + isbn +
                ", description='" + description + '\'' +
                ", inStockNumber=" + inStockNumber +
                ", bookImage=" + bookImage +
                '}';
    }
}
