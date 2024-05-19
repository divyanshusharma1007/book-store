package com.projects.book.store.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookResponseDTO {
    private int pageNumber;
    private int pageSize;
    private List<BookDTO> books;

}