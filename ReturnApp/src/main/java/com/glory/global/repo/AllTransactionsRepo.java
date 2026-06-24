package com.glory.global.repo;

import com.glory.global.entity.AllTransactions;
import com.glory.global.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AllTransactionsRepo extends JpaRepository<AllTransactions, Long>{
    @Query("""
        SELECT DISTINCT t.book
        FROM AllTransactions t
        WHERE t.student.studentId = :studentId
            AND t.isBorrowed = 1
            AND t.isReturned = 0
            AND t.book.bookId NOT IN :bookIds
    """)
    List<Book> findYetToBeReturnedBooksByStudentId(@Param("studentId") Long studentId,
                                                    @Param("bookIds") List<Long> bookIds);

    Optional<AllTransactions> findByStudentStudentIdAndBookBookId(Long studentId, Long bookId);

    @Modifying
    @Query("""
       UPDATE AllTransactions a
       SET a.isReturned = 1,
           a.returnDate = :returnDate
       WHERE a.student.studentId = :studentId
        AND a.book.bookId = :bookId
        AND a.isBorrowed = 1
        AND a.isReturned = 0
       """)
    int updateAllTransactionsInventory(@Param("studentId") Long studentId, @Param("bookId") Long bookId,
                                       @Param("returnDate") Date returnDate);
}