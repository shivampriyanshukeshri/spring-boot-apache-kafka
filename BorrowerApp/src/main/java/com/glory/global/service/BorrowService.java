package com.glory.global.service;

import com.glory.global.dto.BookDTO;
import com.glory.global.dto.BorrowRequestDTO;
import com.glory.global.dto.BorrowResponseDTO;
import com.glory.global.entity.Book;
import com.glory.global.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BorrowService implements IBorrowService{
    @Autowired IInventoryService inventoryService;

    @Override
    public BorrowResponseDTO borrow(BorrowRequestDTO borrowRequest){
        Student reqStudent = null;
        Set<BookDTO> availableBooks = new HashSet<>();
        Map<Long, String> unborrowedBooks = new HashMap<>();

        BorrowResponseDTO borrowResponse = new BorrowResponseDTO();
        borrowResponse.setStudentId(borrowRequest.getStudentId());
        borrowResponse.setBookIdSet(borrowRequest.getBookIdSet());

        Optional<Student> reqStudentContainer = this.inventoryService.studentExists(borrowRequest.getStudentId());

        if(reqStudentContainer == null){
            borrowResponse.setMsg("DATABASE_EXCEPTION");
            return borrowResponse;
        }
        else if(reqStudentContainer.isEmpty()){
            borrowResponse.setMsg("STUDENT_DOES_NOT_EXIST");
            return borrowResponse;
        }
        else reqStudent = reqStudentContainer.get();

        for(Long bookId: borrowRequest.getBookIdSet()){
            Optional<BookDTO> reqBookContainer = this.inventoryService.bookAvailable(bookId);

            if(reqBookContainer == null)
                unborrowedBooks.put(bookId, "DATABASE_EXCEPTION");
            else if(reqBookContainer.isEmpty())
                unborrowedBooks.put(bookId, "BOOK_UNAVAILABLE");
            else{
                try{
                    if(this.inventoryService.isBorrowingPossible(borrowRequest.getStudentId(), bookId))
                        availableBooks.add(reqBookContainer.get());
                    else unborrowedBooks.putIfAbsent(bookId, "YET_TO_RETURN_BOOK_WITH_BOOKID_" + bookId);
                }
                catch(Exception e) {unborrowedBooks.putIfAbsent(bookId, "DATABASE_EXCEPTION");}
            }
        }

        for(BookDTO book: availableBooks){
            try{
                this.inventoryService.borrowBook(reqStudent, book);
                book.setBooksLeft(book.getBooksLeft() - 1);
            }
            catch(Exception e) {unborrowedBooks.putIfAbsent(book.getBookId(), "DATABASE_EXCEPTION");}
        }

        borrowResponse.setBorrowedBooks(availableBooks);
        borrowResponse.setUnborrowedBooks(unborrowedBooks);
        borrowResponse.setMsg(null);

        return borrowResponse;
    }
}