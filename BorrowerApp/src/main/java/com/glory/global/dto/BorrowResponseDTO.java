package com.glory.global.dto;

import com.glory.global.entity.Book;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BorrowResponseDTO{
    private Long studentId;
    private Set<Long> bookIdSet;
    private Set<BookDTO> borrowedBooks;
    private Map<Long, String> unborrowedBooks;
    private String msg;
}