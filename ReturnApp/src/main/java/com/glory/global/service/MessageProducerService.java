package com.glory.global.service;

import com.glory.global.dto.ReturnResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class MessageProducerService implements IMessageProducerService{
    @Autowired private KafkaTemplate<String, ReturnResponseDTO> kafkaTemplate;

    @Override
    public Boolean produceErrorMsg(ReturnResponseDTO returnResponse){
        try{
            this.kafkaTemplate.send("return-request-failed", returnResponse).get();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void produceConfirmationMsg(ReturnResponseDTO returnResponse){
        if(!returnResponse.getReturnedBooks().isEmpty()){
            try{
//                ReturnResponseDTO response = new ReturnResponseDTO();
//                response.setReturnedBooks(returnResponse.getReturnedBooks());
                this.kafkaTemplate.send("returning-successful", returnResponse)
                        .get();
            }
            catch(Exception e) {e.printStackTrace();}
        }

        if(!returnResponse.getUnreturnedBookIdsMap().isEmpty()){
            try{
                this.kafkaTemplate.send("return-request-failed", returnResponse)
                        .get();
            }
            catch(Exception e) {e.printStackTrace();}
        }
    }
}