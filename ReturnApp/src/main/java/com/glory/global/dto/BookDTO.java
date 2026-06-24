package com.glory.global.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"bookId", "title"})
public class BookDTO{
    private Long bookId;
    private String title;
}