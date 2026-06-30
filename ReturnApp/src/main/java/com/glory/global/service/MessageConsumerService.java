package com.glory.global.service;

import com.glory.global.dto.ReturnRequestDTO;
import com.glory.global.dto.ReturnResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class MessageConsumerService implements IMessageConsumerService{
    @Autowired private IReturnService returnService;
    @Autowired private IMessageProducerService messageProducer;

    @KafkaListener(topics="return-init", groupId="library-inventory-group")
    @Override
    public void consumeReturnInitMsg(ReturnRequestDTO returnRequest){
        String msg = this.returnRequestValidator(returnRequest).getMsg();

        if(msg.equals("Validation Successful")){
            ReturnResponseDTO returnResponse = this.returnService.returnBook(returnRequest);

            if(returnResponse.getMsg() != null)
                this.messageProducer.produceErrorMsg(returnResponse);
            else this.messageProducer.produceConfirmationMsg(returnResponse);
        }
        else{
            ReturnResponseDTO returnResponse = new ReturnResponseDTO(returnRequest.getStudentId(),
                    returnRequest.getBookIdSet());

            this.messageProducer.produceErrorMsg(returnResponse);
        }
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