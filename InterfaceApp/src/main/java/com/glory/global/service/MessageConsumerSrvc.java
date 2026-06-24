package com.glory.global.service;

import com.glory.global.dto.BorrowResponseDTO;
import com.glory.global.persistenceStore.BorrowPersistenceStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageConsumerSrvc implements IMessageConsumer{
    @Autowired private BorrowPersistenceStore persistenceStore;

    @KafkaListener(topics="borrow-request-failed", groupId="library-inventory-group")
    @Override
    public void consumeFailedMsg(BorrowResponseDTO borrowResponse){
        Map<Long, BorrowResponseDTO> failedBorrowResponses = this.persistenceStore.getFailedBorrowResponses();

        failedBorrowResponses.put(borrowResponse.getStudentId(), borrowResponse);
    }

    @KafkaListener(topics="borrowing-successful", groupId="library-inventory-group")
    @Override
    public void consumeSuccessfulMsg(BorrowResponseDTO borrowResponse){
        this.persistenceStore.getSuccessfulBorrowResponses()
                .put(borrowResponse.getStudentId(), borrowResponse);
    }
}