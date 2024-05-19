package com.projects.book.store.dto;

import java.util.List;

import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartDTO {

    @OneToMany
    private List<CartItemDTO> cartItems;
}
