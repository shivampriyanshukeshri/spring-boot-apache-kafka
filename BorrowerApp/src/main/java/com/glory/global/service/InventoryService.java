package com.glory.global.service;

import com.glory.global.dto.BookDTO;
import com.glory.global.entity.AllTransactions;
import com.glory.global.entity.Book;
import com.glory.global.entity.Student;
import com.glory.global.repo.AllTransactionsRepo;
import com.glory.global.repo.BookRepo;
import com.glory.global.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class InventoryService implements IInventoryService{
    @Autowired private StudentRepo studentRepo;
    @Autowired private BookRepo bookRepo;
    @Autowired private AllTransactionsRepo allTransactionsRepo;

    @Override
    public Optional<Student> studentExists(Long studentId){
        try {return this.studentRepo.findById(studentId);}
        catch(Exception e){return null;}
    }

    @Override
    public Optional<BookDTO> bookAvailable(Long bookId){
        try{
            Optional<Book> reqBookContainer = this.bookRepo.findById(bookId);

            if(reqBookContainer.isPresent()){
                Book reqBook = reqBookContainer.get();

                if(reqBook.getBooksLeft() >= 1){
                    BookDTO book = new BookDTO(reqBook.getBookId(), reqBook.getTitle(), reqBook.getBooksLeft());
                    return Optional.of(book);
                }
                else return Optional.empty();
            }
            else return Optional.empty();
        }
        catch(Exception e) {return null;}
    }

    @Transactional
    @Override
    public Boolean borrowBook(Student reqStudent, BookDTO reqBook) throws Exception{
        try{
            this.bookRepo.borrowBook(reqBook.getBookId());

            Optional<Book> bookContainer = this.bookRepo.findById(reqBook.getBookId());

            AllTransactions transaction = new AllTransactions(reqStudent, bookContainer.get(), 1, 0);
            transaction.setBorrowDate(new Date());

            this.allTransactionsRepo.save(transaction);

            return true;
        }
        catch(Exception e){
            throw new Exception("DatabaseException: " + reqBook.getBookId());
        }
    }

    @Override
    public Boolean isBorrowingPossible(Long studentId, Long bookId) throws Exception{
        Optional<AllTransactions> reqTransactionDataContainer = Optional.empty();

        try{
            reqTransactionDataContainer = this.allTransactionsRepo.
                    findByStudentStudentIdAndBookBookId(studentId, bookId);
        }
        catch(Exception e){throw new Exception("DatabaseException: " + bookId);}

        if(reqTransactionDataContainer.isEmpty()) return true;

        AllTransactions reqTransactionData = reqTransactionDataContainer.get();

        if(reqTransactionData.getIsBorrowed() == 1 && reqTransactionData.getIsReturned() == 1) return true;

        return false;
    }
}
