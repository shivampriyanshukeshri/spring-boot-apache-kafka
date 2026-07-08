package com.glory.global.dto;

import com.glory.global.entity.Book;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookSlNoDTO{
    private Book book;
    private Long slNo;
}