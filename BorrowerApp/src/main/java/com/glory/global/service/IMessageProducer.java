package com.glory.global.service;

import com.glory.global.dto.BorrowResponseDTO;

public interface IMessageProducer{
    void produceConfirmationMsg(BorrowResponseDTO borrowResponse);
    Boolean produceErrorMsg(BorrowResponseDTO borrowRequest);
}