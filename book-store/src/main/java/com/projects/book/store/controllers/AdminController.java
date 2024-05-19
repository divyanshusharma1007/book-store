package com.projects.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.book.store.dto.BookDTO;
import com.projects.book.store.services.BookService;

@RestController("/admin")
public class AdminController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity<?> addBook(@RequestBody BookDTO book) {
        return bookService.addBook(book);

    }

    @PutMapping("/update-book")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO book) {
        return bookService.updateBook(book);

    }
}