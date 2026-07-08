package com.glory.global.service;

import com.glory.global.dto.BookDTO;
import com.glory.global.entity.Book;
import com.glory.global.entity.Student;

import java.util.Optional;
import java.util.Set;

public interface IInventoryService{
    Optional<Student> studentExists(Long studentId);
    Optional<BookDTO> bookAvailable(Long bookId);
    Long borrowBook(Student reqStudent, BookDTO reqBook) throws Exception;
    Boolean isBorrowingPossible(Long studentId, String bookTitle) throws Exception;
}