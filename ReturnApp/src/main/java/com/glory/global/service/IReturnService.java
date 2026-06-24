package com.glory.global.service;

import com.glory.global.dto.ReturnRequestDTO;
import com.glory.global.dto.ReturnResponseDTO;

public interface IReturnService{
    ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest);
}