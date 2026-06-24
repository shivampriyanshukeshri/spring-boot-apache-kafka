package com.glory.global.service;

import com.glory.global.dto.BorrowRequestDTO;
import com.glory.global.dto.BorrowResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerSrvc implements IMessageConsumer{
    @Autowired private IBorrowService borrowService;
    @Autowired private IMessageProducer messageProducer;

    @KafkaListener(topics="borrow-init", groupId="library-inventory-group")
    @Override
    public void consumeBorrowInitMsg(BorrowRequestDTO borrowRequest){
        String msg = this.borrowRequestValidator(borrowRequest).getMsg();

        if(msg.equals("Validation Successful")){
            BorrowResponseDTO borrowResponse = this.borrowService.borrow(borrowRequest);

            if(borrowResponse.getMsg() != null)
                this.messageProducer.produceErrorMsg(borrowResponse);
            else this.messageProducer.produceConfirmationMsg(borrowResponse);
        }
        else{
            BorrowResponseDTO borrowResponse = new BorrowResponseDTO(
                    borrowRequest.getStudentId(), borrowRequest.getBookIdSet(),
                    null, null, msg
            );

            this.messageProducer.produceErrorMsg(borrowResponse);
        }
    }

    private BorrowRequestDTO borrowRequestValidator(BorrowRequestDTO borrowRequest){
        if(borrowRequest.getStudentId() == null)
            borrowRequest.setMsg("Validation Failed: Student ID can't be null");
        else if(borrowRequest.getBookIdSet().isEmpty())
            borrowRequest.setMsg("Validation Failed: Book IDs can't be null");
        else borrowRequest.setMsg("Validation Successful");

        return borrowRequest;
    }
}
