package com.glory.global.service;

import com.glory.global.dto.BookDTO;
import com.glory.global.dto.ReturnRequestDTO;
import com.glory.global.dto.ReturnResponseDTO;
import com.glory.global.entity.Book;
import com.glory.global.entity.Student;
import com.glory.global.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReturnService implements IReturnService{
    @Autowired private IInventoryService inventoryService;

    @Override
    public ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest){
        Student reqStudent = null;
        Map<Long, String> unreturnedBookIdsMap = new HashMap<>();
        Set<BookDTO> returnedBooksSet = new HashSet<>();

        Set<Long> bookIdSet = returnRequest.getBookIdSet();

        ReturnResponseDTO returnResponse = new ReturnResponseDTO();
        returnResponse.setStudentId(returnRequest.getStudentId());
        returnResponse.setBookIdSet(bookIdSet);

        Optional<Student> reqStudentContainer = this.inventoryService.studentExists(returnRequest.getStudentId());

        if(reqStudentContainer == null){
            returnResponse.setMsg("DATABASE_EXCEPTION");
            return returnResponse;
        }
        else if(reqStudentContainer.isEmpty()){
            returnResponse.setMsg("STUDENT_DOES_NOT_EXIST");
            return returnResponse;
        }
        else reqStudent = reqStudentContainer.get();

        Set<BookDTO> yetToBeReturnedBooks = this.inventoryService.yetToBeReturnedBookSet(returnRequest.getStudentId(),
                returnRequest.getBookIdSet());

        if(yetToBeReturnedBooks == null){
            returnResponse.setMsg("DATABASE_EXCEPTION");
            return returnResponse;
        }
        else{
            for(Long bookId: bookIdSet){
                try{
                    this.inventoryService.returnBook(reqStudent, bookId);

                    String bookTitle = this.inventoryService.getBookTitleBookId(bookId);

                    if(bookTitle == null)
                        unreturnedBookIdsMap.putIfAbsent(bookId, "BOOKID_NOT_FOUND");
                    else{
                        BookDTO returnedBook = new BookDTO(bookId, bookTitle);
                        returnedBooksSet.add(returnedBook);
                    }
                }
                catch(Exception e){
                    String msg = e.getMessage();

                    if(msg.equals("EITHER_THE_BOOK_WAS_NEVER_BORROWED_OR_HAS_BEEN_BORROWED_AND_RETURNED_SUCCESSFULLY")
                            || msg.equals("BOOKID_NOT_FOUND")
                    )
                        unreturnedBookIdsMap.putIfAbsent(bookId, msg);
                    else unreturnedBookIdsMap.putIfAbsent(bookId, "DATABASE_EXCEPTION");
                }
            }

            returnResponse.setReturnedBooks(returnedBooksSet);
            returnResponse.setYetToReturnBooks(yetToBeReturnedBooks);
            returnResponse.setUnreturnedBookIdsMap(unreturnedBookIdsMap);

            return returnResponse;
        }
    }
}