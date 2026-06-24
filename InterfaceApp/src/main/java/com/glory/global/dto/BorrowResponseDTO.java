package com.glory.global.dto;

import com.glory.global.dto.BookDTO;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of={"studentId", "bookIdSet"})
public class BorrowResponseDTO{
    private Long studentId;
    private Set<Long> bookIdSet;
    private Set<BookDTO> borrowedBooks;
    private Map<Long, String> unborrowedBooks;
    private String msg;
}
