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
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long studentId;

    @Column(length=30, nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private Long mobileNo;

    @OneToMany(mappedBy = "student")
    private List<AllTransactions> transactions;
}