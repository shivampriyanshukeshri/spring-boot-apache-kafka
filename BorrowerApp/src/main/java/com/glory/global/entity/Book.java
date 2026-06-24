package com.glory.global.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Book{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long bookId;

    @Column(length=50, nullable=false, unique=true)
    private String title;

    @Column(nullable=false)
    private Integer booksLeft;

    @OneToMany(mappedBy = "book")
    private List<AllTransactions> transactions;
}