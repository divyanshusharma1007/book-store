package com.projects.book.store.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projects.book.store.dto.UserDTO;
import com.projects.book.store.exception.AlreadyExists;
import com.projects.book.store.exception.InvalidCredential;
import com.projects.book.store.model.User;

@Service
public interface UserService {

    ResponseEntity<?> login(String email, String password) throws InvalidCredential;

    ResponseEntity<?> register(UserDTO user) throws AlreadyExists;

    ResponseEntity<?> getUser(User user);

    ResponseEntity<?> purchaseHistory(User user);

    ResponseEntity<?> getCart(User user);

    ResponseEntity<?> deleteItemFromCart(Long id, User user);

    ResponseEntity<?> purchaseItemFromCart(Long id, User user);

    ResponseEntity<?> proccedCart(User user);

    ResponseEntity<?> getCartItem(User user, Long id);

}