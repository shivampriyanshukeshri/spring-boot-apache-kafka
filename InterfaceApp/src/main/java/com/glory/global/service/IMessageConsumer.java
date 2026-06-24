package com.glory.global.service;

import com.glory.global.dto.BorrowResponseDTO;

public interface IMessageConsumer{
    void consumeFailedMsg(BorrowResponseDTO borrowResponse);
    void consumeSuccessfulMsg(BorrowResponseDTO borrowResponse);
}