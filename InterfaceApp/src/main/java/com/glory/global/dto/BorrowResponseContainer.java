package com.glory.global.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BorrowResponseContainer{
    private BorrowResponseDTO successfulBorrowResponse;
    private BorrowResponseDTO failedBorrowResponse;
}