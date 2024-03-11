package com.library.library.model;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import com.library.library.model.security.User;

@Entity
public class BorowedItem implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="book_id")
    //@OrderBy("title")
    private Book book;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate borowedDate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dueDate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate returnDate;

    @Transient
    private long overdueDays;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private boolean returned;

    public BorowedItem() {  }

    public BorowedItem( Book book, LocalDate borowedDate, LocalDate dueDate, LocalDate returnDate, User user, boolean returned) {
       // this.id = id;
        this.book = book;
        this.borowedDate = borowedDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.user = user;
        this.returned = returned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorowedDate() {
        return borowedDate;
    }

    public void setBorowedDate(LocalDate borowedDate) {
        this.borowedDate = borowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(long overdueDays) {
        LocalDate now = LocalDate.now();
        // LocalDate dueDate = LocalDate.ofInstant(this.dueDate.toInstant(), ZoneId.systemDefault());
        int compare = now.compareTo(dueDate);

        //  long days = ChronoUnit.DAYS.between(now, dd) ;
        this.overdueDays = overdueDays;
    }

    @Override
    public boolean isNew() {
        return true;
    }



}
