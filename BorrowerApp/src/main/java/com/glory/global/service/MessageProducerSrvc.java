package com.glory.global.service;

import com.glory.global.dto.BorrowResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerSrvc implements IMessageProducer{
    @Autowired private KafkaTemplate<String, BorrowResponseDTO> kafkaTemplate;

    @Override
    public void produceConfirmationMsg(BorrowResponseDTO borrowResponse){
        if(!borrowResponse.getBorrowedBooks().isEmpty()){
            try{
//                BorrowResponseDTO response = new BorrowResponseDTO();
//                response.setBorrowedBooks(borrowResponse.getBorrowedBooks());
                this.kafkaTemplate.send("borrowing-successful", borrowResponse)
                        .get();
            }
            catch(Exception e) {e.printStackTrace();}
        }

        if(!borrowResponse.getUnborrowedBooks().isEmpty()){
            try{
                this.kafkaTemplate.send("borrow-request-failed", borrowResponse)
                        .get();
            }
            catch(Exception e) {e.printStackTrace();}
        }
    }

    @Override
    public Boolean produceErrorMsg(BorrowResponseDTO borrowResponse){
        try{
            this.kafkaTemplate.send("borrow-request-failed", borrowResponse).get();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
