package com.library.library.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.library.library.model.security.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Entity
public class ReservedItem implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Book book;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate reservedDate;
    private boolean collected;

    @ManyToOne//(mappedBy="reservedItem")
    @JoinColumn(name = "userId")
    private User user;

    public ReservedItem() {  }

    public ReservedItem( Book book, LocalDate reservedDate, boolean collected, User user) {
       // this.id = id;
        this.book = book;
        this.reservedDate = reservedDate;
        this.collected = collected;
        this.user = user;
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

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(LocalDate reservedDate) {
        this.reservedDate = reservedDate;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservedItem that)) return false;
        return isCollected() == that.isCollected() && Objects.equals(getId(), that.getId()) && Objects.equals(getBook(), that.getBook()) && Objects.equals(getReservedDate(), that.getReservedDate()) && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBook(), getReservedDate(), isCollected(), getUser());
    }

    @Override
    public String toString() {
        return "ReservedItem{" +
                "id=" + id +
                ", book=" + book +
                ", reservedDate=" + reservedDate +
                ", collected=" + collected +
                ", user=" + user +
                '}';
    }
}
