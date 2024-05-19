package com.projects.book.store.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseHistoryDTO {
    private Long id;
    private List<PurchaseItemDTO> purchaseItems;
}
