package com.glory.global.service;

import com.glory.global.dto.BorrowRequestDTO;
import com.glory.global.dto.BorrowResponseDTO;

import java.util.Set;

public interface IBorrowService{
    BorrowResponseDTO borrow(BorrowRequestDTO borrowRequest);
}