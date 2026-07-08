package com.glory.global.service;

import com.glory.global.dto.BorrowRequestDTO;
import com.glory.global.dto.ReturnRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerSrvc implements IMessageProducer{
    @Autowired private KafkaTemplate<String, BorrowRequestDTO> borrowTemplate;
    @Autowired private KafkaTemplate<String, ReturnRequestDTO> returnTemplate;

    @Override
    public String produceBorrowInitMsg(BorrowRequestDTO borrowRequest){
        borrowRequest.setMsg("BORROW_REQUEST_INITIATED");

        try{
            borrowRequest.setMsg("BORROW_INIT_MESSAGE_SUCCESS");
            this.borrowTemplate.send("borrow-init", borrowRequest).get();
            return "BORROW_INIT_MESSAGE_SUCCESS";
        }
        catch (Exception e){
            e.printStackTrace();
            return "BORROW_INIT_MESSAGE_FAIL";
        }
    }

    @Override
    public String produceReturnInitMsg(ReturnRequestDTO returnRequest) {
        //returnRequest.setMsg("RETURN_REQUEST_INITIATED");

        try{
            this.returnTemplate.send("return-init", returnRequest).get();
            returnRequest.setMsg("RETURN_REQUEST_INITIATED");
            return "RETURN_INIT_MESSAGE_SUCCESS";
        }
        catch (Exception e){
            e.printStackTrace();
            returnRequest.setMsg("RETURN_REQUEST_INITTATION_FAILED");
            return "RETURN_INIT_MESSAGE_FAIL";
        }
    }
}