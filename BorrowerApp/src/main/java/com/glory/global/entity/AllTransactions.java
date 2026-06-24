package com.glory.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class AllTransactions{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @Column(nullable=false)
    private Integer isBorrowed = 0;

    @Column(nullable=false)
    private Integer isReturned = 0;

    @Column private Date borrowDate = null;

    @Column private Date returnDate = null;

    public AllTransactions(Student student, Book book, Integer isBorrowed, Integer isReturned){
        super();
        this.student = student;
        this.book = book;
        this.isBorrowed = isBorrowed;
        this.isReturned = isReturned;
    }
}