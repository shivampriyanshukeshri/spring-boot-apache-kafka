package com.glory.global.repo;

import com.glory.global.dto.BookSlNoDTO;
import com.glory.global.entity.AllTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AllTransactionsRepo extends JpaRepository<AllTransactions, Long>{
    @Query("""
        SELECT DISTINCT new com.glory.global.dto.BookSlNoDTO(t.book, t.slNo)
        FROM AllTransactions t
        WHERE t.student.studentId = :studentId
            AND t.isBorrowed = 1
            AND t.isReturned = 0
            AND t.slNo NOT IN :slNo
    """)
    List<BookSlNoDTO> findYetToBeReturnedBooksByStudentId(@Param("studentId") Long studentId,
                                                          @Param("slNo") List<Long> slNo);

    @Query("""
        SELECT COUNT (t)
        FROM AllTransactions t
        WHERE t.student.studentId = :studentId
            AND t.book.bookId = :bookId
            AND t.slNo = :slNo
            AND t.isBorrowed = 1
            AND t.isReturned = 0
    """)
    long isReturnPossible(@Param("studentId") Long studentId, @Param("bookId") Long bookId, @Param("slNo") Long slNo);

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