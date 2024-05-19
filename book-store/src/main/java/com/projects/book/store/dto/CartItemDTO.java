package com.projects.book.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartItemDTO {

    @NotBlank
    @NotNull
    private BookDTO book;

    @NotNull
    @NotBlank
    private Integer quantity;
}
