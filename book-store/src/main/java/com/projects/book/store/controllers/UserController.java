package com.projects.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.book.store.model.User;
import com.projects.book.store.services.BookService;
import com.projects.book.store.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    /**
     * used to get the user information
     * 
     * @param request
     * @return
     */

    @GetMapping("")
    public ResponseEntity<?> getUser(@RequestAttribute User user) {

        return userService.getUser(user);
    }

    /**
     * used to get teh details of all books in the paginated format
     * 
     * @param request
     * @return
     */
    @GetMapping("/books")
    public ResponseEntity<?> getBooks(@RequestParam @NotEmpty @NotBlank Integer pageNumber,
            @RequestParam @NotEmpty @NotBlank Integer pageSize) {
        return bookService.findAll(pageSize, pageNumber);
    }

    /**
     * used to get the paritcular single book
     * 
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") Long id) {

        return bookService.findById(id);
    }

    /**
     * used to get the purchase history of the particualar user
     * 
     * @param request
     * @return
     */
    @GetMapping("/purchase-history")
    public ResponseEntity<?> purchaseHistory(@RequestAttribute User user) {
        return userService.purchaseHistory(user);
    }

    /**
     * used to get the cart of the user
     * 
     * @param request
     * @return
     */
    @GetMapping("/cart")
    public ResponseEntity<?> getCart(@RequestAttribute User user) {
        return userService.getCart(user);
    }

    /**
     * used to get the details of particular cart item
     * 
     * @param request
     * @param id
     * @return
     */
    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getCartItem(@RequestAttribute User user, @PathVariable Long id) {
        return userService.getCartItem(user, id);
    }

    /**
     * used to proceed all cart
     * 
     * @param request
     * @return
     */
    @PutMapping("/cart-procced")
    public ResponseEntity<?> getCartProcced(@RequestAttribute User user) {
        return userService.proccedCart(user);

    }

    /**
     * delete the particular product from the cart
     * 
     * @param request
     * @param id
     * @return
     */
    @DeleteMapping("/delete-cart-item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id, @RequestAttribute User user) {

        return userService.deleteItemFromCart(id, user);
    }

    /**
     * used to purchase the single product from the cart
     * 
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/purchase-item/{id}")
    public ResponseEntity<?> purchaseItem(@PathVariable Long id, @RequestAttribute User user) {
        return userService.purchaseItemFromCart(id, user);

    }

}
