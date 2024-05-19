package com.projects.book.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.book.store.model.PurchaseItem;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {

    
}