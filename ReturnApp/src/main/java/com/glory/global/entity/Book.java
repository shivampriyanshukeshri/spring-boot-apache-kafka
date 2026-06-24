package com.glory.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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