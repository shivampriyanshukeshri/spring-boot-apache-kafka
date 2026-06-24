package com.glory.global.service;

import com.glory.global.dto.BorrowRequestDTO;

public interface IMessageConsumer{
    void consumeBorrowInitMsg(BorrowRequestDTO borrowRequest);
}