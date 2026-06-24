package com.glory.global.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO{
    private Long bookId;
    private String title;
    private Integer booksLeft;
}
