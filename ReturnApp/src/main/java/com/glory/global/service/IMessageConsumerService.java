package com.glory.global.service;

import com.glory.global.dto.ReturnRequestDTO;

public interface IMessageConsumerService{
    void consumeReturnInitMsg(ReturnRequestDTO returnRequest);
}
