package com.glory.global.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO{
    private Long bookId;
    private String title;
    private Integer booksLeft;
}