package com.glory.global.service;

import com.glory.global.dto.BorrowRequestDTO;
import com.glory.global.dto.ReturnRequestDTO;

public interface IMessageProducer{
    String produceBorrowInitMsg(BorrowRequestDTO borrowRequest);
    String produceReturnInitMsg(ReturnRequestDTO returnRequest);
}