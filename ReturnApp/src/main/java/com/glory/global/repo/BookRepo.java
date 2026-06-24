package com.glory.global.repo;

import com.glory.global.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepo extends JpaRepository <Book, Long>{
    @Modifying
    @Query("""
       UPDATE Book b
       SET b.booksLeft = b.booksLeft + 1
       WHERE b.bookId = :bookId
       """)
    int updateBookInventory(Long bookId);

    Optional<Book> findByTitle(String title);
}