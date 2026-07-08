package com.glory.global.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class BookDTO{
    @NonNull private Long bookId;
    @NonNull private String title;
    private Long slNo;
    @NonNull private Integer booksLeft;
}
