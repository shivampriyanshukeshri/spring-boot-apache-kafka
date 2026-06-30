package com.glory.global.service;

import com.glory.global.dto.BookDTO;
import com.glory.global.dto.ReturnResponseDTO;
import com.glory.global.entity.AllTransactions;
import com.glory.global.entity.Book;
import com.glory.global.entity.Student;
import com.glory.global.repo.AllTransactionsRepo;
import com.glory.global.repo.BookRepo;
import com.glory.global.repo.StudentRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryService implements IInventoryService{
    @Autowired private StudentRepo studentRepo;
    @Autowired private AllTransactionsRepo allTransactionsRepo;
    @Autowired BookRepo bookRepo;

    @Override
    public String getBookTitleByBookId(Long bookId) throws Exception{
        try{
            Optional<Book> reqBookContainer = this.bookRepo.findById(bookId);

            if(reqBookContainer.isEmpty()) return null;

            return reqBookContainer.get().getTitle();
        }
        catch(Exception e) {throw new Exception("DATABASE_EXCEPTION");}
    }

    @Override
    public Optional<Student> studentExists(Long studentId){
        try {return this.studentRepo.findById(studentId);}
        catch(Exception e){return null;}
    }

    @Override
    public Set<BookDTO> yetToBeReturnedBookSet(Long studentId, Set<Long> bookIdSet){
        try{
            List<Book> yetToBeReturnedBooks = this.allTransactionsRepo.findYetToBeReturnedBooksByStudentId(studentId,
                    new ArrayList<>(bookIdSet));

            Set<BookDTO> stillNotReturnedBooks = new HashSet<>();

            for(Book book: yetToBeReturnedBooks){
                BookDTO bookDTO = new BookDTO(book.getBookId(), book.getTitle());
                stillNotReturnedBooks.add(bookDTO);
            }

            return stillNotReturnedBooks;
        }
        catch(Exception e){return null;}
    }

    @Override
    public Long isReturnPossible(Long studentId, Long bookId) throws Exception{
        Optional<AllTransactions> reqTransactionDataContainer = Optional.empty();

        try{
            reqTransactionDataContainer = this.allTransactionsRepo.findByStudentStudentIdAndBookBookId(studentId,
                    bookId);
        }
        catch(Exception e) {throw new Exception("DATABASE_EXCEPTION");}

        if(reqTransactionDataContainer.isEmpty()) return null;

        AllTransactions reqTransactionData = reqTransactionDataContainer.get();

        if(reqTransactionData.getIsBorrowed() == 1 && reqTransactionData.getIsReturned() == 0)
            return reqTransactionData.getTransactionId();

        return null;
    }

    @Transactional
    @Override
    public Boolean returnBook(Student reqStudent, Long bookId) throws Exception{
        if(this.bookRepo.updateBookInventory(bookId) != 0){
            Long studentId = reqStudent.getStudentId();

                if(this.allTransactionsRepo.updateAllTransactionsInventory(studentId, bookId, new Date()) != 0)
                    return true;
                else throw new Exception("EITHER_THE_BOOK_WAS_NEVER_BORROWED_OR_HAS_BEEN_BORROWED_AND_RETURNED_SUCCESSFULLY");
        }
        else throw new Exception("BOOKID_NOT_FOUND");
    }
}