package com.glory.global.service;

import com.glory.global.dto.BookDTO;
import com.glory.global.dto.BookSlNoDTO;
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
    public Set<BookDTO> yetToBeReturnedBookSet(Long studentId, Map<Long, Long> bookIdSlNoMap){
        try{
            List<BookSlNoDTO> yetToBeReturnedBooks = this.allTransactionsRepo.findYetToBeReturnedBooksByStudentId(studentId,
                    new ArrayList<>(bookIdSlNoMap.values()));

            Set<BookDTO> stillNotReturnedBooks = new HashSet<>();

            for(BookSlNoDTO it: yetToBeReturnedBooks){
                BookDTO bookDTO = new BookDTO(it.getBook().getBookId(), it.getBook().getTitle(), it.getSlNo());
                stillNotReturnedBooks.add(bookDTO);
            }

            return stillNotReturnedBooks;
        }
        catch(Exception e){return null;}
    }

    @Override
    public Boolean isReturnPossible(Long studentId, Long bookId, Long slNo) throws Exception{
        Optional<AllTransactions> reqTransactionDataContainer = Optional.empty();

        try{
//            reqTransactionDataContainer = this.allTransactionsRepo.findByStudentStudentIdAndBookBookId(studentId,
//                    bookId);

            return this.allTransactionsRepo.isReturnPossible(studentId, bookId, slNo) == 1;

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