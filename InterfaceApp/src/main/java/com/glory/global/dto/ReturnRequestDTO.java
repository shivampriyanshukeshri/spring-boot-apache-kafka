package com.glory.global.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Setter
@Getter
@ToString
public class ReturnRequestDTO{
    private Long studentId;
    private Set<Long> bookIdSet;
    private String msg;
}
