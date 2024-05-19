package com.projects.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.book.store.dto.LoginDTO;
import com.projects.book.store.dto.UserDTO;
import com.projects.book.store.exception.AlreadyExists;
import com.projects.book.store.exception.InvalidCredential;
import com.projects.book.store.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class IndexController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginRequest) throws InvalidCredential {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUp(@Valid @RequestBody UserDTO user) throws AlreadyExists {
        return userService.register(user);
    }

}
