package com.projects.book.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.book.store.model.Cart;

public interface CartRespository extends JpaRepository<Cart, Long> {

}