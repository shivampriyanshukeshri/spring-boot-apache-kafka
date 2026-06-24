package com.glory.global.controller;

import com.glory.global.dto.BorrowRequestDTO;
import com.glory.global.dto.BorrowResponseContainer;
import com.glory.global.dto.BorrowResponseDTO;
import com.glory.global.dto.ReturnRequestDTO;
import com.glory.global.persistenceStore.BorrowPersistenceStore;
import com.glory.global.service.IMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mainController")
public class MainController{
    @Autowired private IMessageProducer messageProducer;
    @Autowired private BorrowPersistenceStore persistenceStore;

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRequestDTO> borrowRequest(@RequestBody BorrowRequestDTO borrowRequest){
        if(borrowRequest == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else if(this.borrowRequestValidator(borrowRequest).getMsg().equals("Validation Successful")){
            String msg = this.messageProducer.produceBorrowInitMsg(borrowRequest);
            borrowRequest.setMsg(msg);

            if(msg.equals("BORROW_INIT_MESSAGE_FAIL"))
                return new ResponseEntity<>(borrowRequest, HttpStatus.INTERNAL_SERVER_ERROR);
            else return new ResponseEntity<>(borrowRequest, HttpStatus.OK);
        }
        else return new ResponseEntity<>(borrowRequest, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/respondBorrowRequest/{studentId}")
    public ResponseEntity<BorrowResponseContainer> respondBorrowRequest(@PathVariable Long studentId){
        if(studentId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try{
            BorrowResponseContainer container = new BorrowResponseContainer();

            Map<Long, BorrowResponseDTO> successfulBorrowResponses = this.persistenceStore.getSuccessfulBorrowResponses();
            Map<Long, BorrowResponseDTO> failedBorrowResponses = this.persistenceStore.getFailedBorrowResponses();

            if (successfulBorrowResponses.containsKey(studentId)){
                container.setSuccessfulBorrowResponse(successfulBorrowResponses.get(studentId));

                successfulBorrowResponses.remove(studentId);
            }

            if (failedBorrowResponses.containsKey(studentId)){
                container.setFailedBorrowResponse(failedBorrowResponses.get(studentId));

                failedBorrowResponses.remove(studentId);
            }

            return new ResponseEntity<>(container, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnRequestDTO> returnBook(@RequestBody ReturnRequestDTO returnRequest){
        if(returnRequest == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else if(this.returnRequestValidator(returnRequest).getMsg().equals("Validation Successful")){
            String msg = this.messageProducer.produceReturnInitMsg(returnRequest);

            returnRequest.setMsg(msg);

            if(msg.equals("RETURN_INIT_MESSAGE_FAIL"))
                return new ResponseEntity<>(returnRequest, HttpStatus.INTERNAL_SERVER_ERROR);
            else return new ResponseEntity<>(returnRequest, HttpStatus.OK);
        }
        else return new ResponseEntity<>(returnRequest, HttpStatus.BAD_REQUEST);
    }

    private BorrowRequestDTO borrowRequestValidator(BorrowRequestDTO borrowRequest){
        if(borrowRequest.getStudentId() == null)
            borrowRequest.setMsg("Validation Failed: Student ID can't be null");
        else if(borrowRequest.getBookIdSet().isEmpty())
            borrowRequest.setMsg("Validation Failed: Book IDs can't be null");
        else borrowRequest.setMsg("Validation Successful");

        return borrowRequest;
    }

    private ReturnRequestDTO returnRequestValidator(ReturnRequestDTO returnRequest){
        if(returnRequest.getStudentId() == null)
            returnRequest.setMsg("Validation Failed: Student ID can't be null");
        else if(returnRequest.getBookIdSet().isEmpty())
            returnRequest.setMsg("Validation Failed: Book IDs can't be null");
        else returnRequest.setMsg("Validation Successful");

        return returnRequest;
    }
}