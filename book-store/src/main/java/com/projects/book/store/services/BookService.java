package com.projects.book.store.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projects.book.store.dto.BookDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Service
public interface BookService {

    ResponseEntity<?> findAll(@NotEmpty @NotBlank Integer pageSize, @NotEmpty @NotBlank Integer pageNumber);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> addBook(BookDTO book);

    ResponseEntity<?> updateBook(BookDTO book);

}
