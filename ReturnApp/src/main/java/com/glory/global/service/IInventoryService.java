package com.glory.global.service;

import com.glory.global.dto.BookDTO;
import com.glory.global.entity.Student;

import java.util.Optional;
import java.util.Set;

public interface IInventoryService{
    String getBookTitleByBookId(Long bookId) throws Exception;
    Optional<Student> studentExists(Long studentId);
    Set<BookDTO> yetToBeReturnedBookSet(Long studentId, Set<Long> bookIdSet);
    Long isReturnPossible(Long studentId, Long bookId) throws Exception;
    Boolean returnBook(Student reqStudent, Long bookId) throws Exception;
}