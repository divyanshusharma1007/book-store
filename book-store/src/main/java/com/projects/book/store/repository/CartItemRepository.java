package com.projects.book.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.book.store.model.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    
}
