package com.projects.book.store.servicesImpl;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.book.store.constants.Messages;
import com.projects.book.store.dto.AuthResponseDTO;
import com.projects.book.store.dto.BookDTO;
import com.projects.book.store.dto.CartDTO;
import com.projects.book.store.dto.CartItemDTO;
import com.projects.book.store.dto.PurchaseHistoryDTO;
import com.projects.book.store.dto.PurchaseItemDTO;
import com.projects.book.store.dto.UserDTO;
import com.projects.book.store.exception.AlreadyExists;
import com.projects.book.store.exception.InvalidCredential;
import com.projects.book.store.exception.NotFound;
import com.projects.book.store.model.Cart;
import com.projects.book.store.model.CartItem;
import com.projects.book.store.model.PurchaseHistory;
import com.projects.book.store.model.PurchaseItem;
import com.projects.book.store.model.User;
import com.projects.book.store.repository.UserRepository;
import com.projects.book.store.services.UserService;
import com.projects.book.store.util.JwtUtil;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ModelMapper mapper;

  @Override
  public ResponseEntity<?> login(String email, String password) throws InvalidCredential {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    User user = optionalUser.get();
    if (optionalUser.isEmpty())
      throw new NotFound(Messages.USER_NOT_FOUND);
    if (!passwordEncoder.matches(user.getPassword(), password)) {
      throw new InvalidCredential(Messages.INVALID_CREDENTIAL);
    }
    String authToken = JwtUtil.generateToken(String.valueOf(user.getId()));
    return ResponseEntity.ok(new AuthResponseDTO(user.getRole(), authToken));
  }

  @Override
  public ResponseEntity<?> register(@Valid UserDTO user) throws AlreadyExists {
    Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
    if (optionalUser.isPresent())
      throw new AlreadyExists(Messages.USER_ALREADY_EXISTS);
    User savingUser = mapper.map(user, User.class);
    savingUser.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(savingUser);
    String authToken = JwtUtil.generateToken(String.valueOf(savedUser.getId()));
    return ResponseEntity.ok(new AuthResponseDTO(user.getRole(), authToken));

  }

  @Override
  public ResponseEntity<?> getUser(User user) {
    return ResponseEntity.ok(mapper.map(user, UserDTO.class));
  }

  @Override
  public ResponseEntity<?> purchaseHistory(User user) {
    Optional<PurchaseHistory> purchaseHistoryOptional = userRepository.findById(user.getId())
        .map(User::getPurchaseHistory);
    if (purchaseHistoryOptional.isEmpty())
      throw new NotFound(Messages.NO_PURCHASE_HISTORY);
    PurchaseHistory purchaseHistory = purchaseHistoryOptional.get();
    List<PurchaseItemDTO> purchaseItems = purchaseHistory.getPurchaseItems().stream()
        .map(e -> {
          PurchaseItemDTO purchaseItemDTO = mapper.map(e, PurchaseItemDTO.class);
          BookDTO bookDTO = mapper.map(e.getBook(), BookDTO.class);
          purchaseItemDTO.setBook(bookDTO);
          return purchaseItemDTO;
        })
        .collect(Collectors.toList());

    PurchaseHistoryDTO purchaseHistoryDTO = new PurchaseHistoryDTO();
    purchaseHistoryDTO.setId(purchaseHistory.getId());
    purchaseHistoryDTO.setPurchaseItems(purchaseItems);

    return ResponseEntity.ok(purchaseHistoryDTO);
  }

  @Override
  public ResponseEntity<?> getCart(User user) {
    Cart cart = user.getCart();
    List<CartItem> cartItem = cart.getCartItems();
    CartDTO cartDto = new CartDTO();
    cartDto.setCartItems(cartItem.stream().map(e -> {
      CartItemDTO item = mapper.map(e, CartItemDTO.class);
      item.setBook(mapper.map(e.getBook(), BookDTO.class));
      return item;
    }).collect(Collectors.toList()));

    return ResponseEntity.ok(cartDto);
  }

  @Override
  public ResponseEntity<?> deleteItemFromCart(Long id, User user) {
    Cart cart = user.getCart();
    cart.setCartItems(
        cart.getCartItems().stream().filter(item -> !item.getId().equals(id)).collect(Collectors.toList()));
    user.setCart(cart);
    userRepository.save(user);
    return getCart(user);
  }

  @Override
  public ResponseEntity<?> purchaseItemFromCart(Long id, User user) {
    Optional<CartItem> optionalItem = user.getCart().getCartItems().stream()
        .filter(item -> item.getId().equals(id))
        .findFirst();
    if (optionalItem.isEmpty())
      throw new NotFound(Messages.BOOK_NOT_FOUND);
    CartItem item = optionalItem.get();
    PurchaseItem purchaseItem = new PurchaseItem();
    purchaseItem.setOrderedDate(LocalDate.now());
    purchaseItem.setBook(item.getBook());
    purchaseItem.setQuantity(item.getQuantity());
    user.getPurchaseHistory().getPurchaseItems().add(purchaseItem);
    user.getCart().getCartItems().remove(item);
    userRepository.save(user);
    return purchaseHistory(user);
  }

  @Override
  public ResponseEntity<?> proccedCart(User user) {
    user.getCart().getCartItems().forEach(e -> purchaseItemFromCart(e.getId(), user));
    return purchaseHistory(user);
  }

  @Override
  public ResponseEntity<?> getCartItem(User user, Long id) {
    Optional<CartItem> cartItem = user.getCart().getCartItems().stream().filter(item -> item.getId().equals(id))
        .findFirst();
    if (cartItem.isEmpty())
      throw new NotFound(Messages.ITEM_NOT_FOUND);
    return ResponseEntity.ok(cartItem);
  }

}
