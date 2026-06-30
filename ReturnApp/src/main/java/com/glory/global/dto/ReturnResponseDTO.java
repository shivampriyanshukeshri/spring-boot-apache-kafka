package com.glory.global.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
@ToString
public class ReturnResponseDTO{
    private Long studentId;
    private Set<Long> bookIdSet;
    private Set<BookDTO> returnedBooks;
    Map<Long, String> unreturnedBookIdsMap;
    private Set<BookDTO> yetToReturnBooks;
    private String msg;

    public ReturnResponseDTO(Long studentId, Set<Long> bookIdSet){
        this.studentId = studentId;
        this.bookIdSet = bookIdSet;
    }
}