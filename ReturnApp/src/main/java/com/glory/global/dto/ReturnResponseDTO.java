package com.glory.global.dto;

import lombok.*;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class ReturnResponseDTO{
    @NonNull private Long studentId;
    @NonNull private Map<Long, Long> bookIdSlNoMap;
    private Set<BookDTO> returnedBooks;
    Map<Long, String> unreturnedBookIdsMap;
    private Set<BookDTO> yetToReturnBooks;
    private String msg;

//    public ReturnResponseDTO(Long studentId, Set<Long> bookIdSet){
//        this.studentId = studentId;
//        this.bookIdSet = bookIdSet;
//    }
}