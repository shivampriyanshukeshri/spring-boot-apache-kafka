package com.glory.global.repo;

import com.glory.global.entity.AllTransactions;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface AllTransactionsRepo extends JpaRepository<AllTransactions, Long>{
    Optional<AllTransactions> findByStudentStudentIdAndBookTitle(Long studentId, String bookTitle);
}