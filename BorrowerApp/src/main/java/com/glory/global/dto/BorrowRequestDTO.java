package com.glory.global.dto;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestDTO{
    private Long studentId;
    private Set<Long> bookIdSet;
    private String msg;
}