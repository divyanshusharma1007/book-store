package com.projects.book.store.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class PurchaseItemDTO {
    private Long id;
    private BookDTO book;
    private LocalDate orderedDate;
    private Integer quantity;
}
