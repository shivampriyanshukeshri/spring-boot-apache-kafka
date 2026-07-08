package com.glory.global.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
@ToString
public class ReturnRequestDTO{
    private Long studentId;
    private Map<Long, Long> bookIdSlNoMap;
    //private Set<Long> bookIdSet;
    private String msg;
}