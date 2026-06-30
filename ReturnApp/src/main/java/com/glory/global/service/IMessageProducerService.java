package com.glory.global.service;

import com.glory.global.dto.ReturnResponseDTO;

public interface IMessageProducerService{
    Boolean produceErrorMsg(ReturnResponseDTO returnResponse);
    void produceConfirmationMsg(ReturnResponseDTO borrowResponse);
}