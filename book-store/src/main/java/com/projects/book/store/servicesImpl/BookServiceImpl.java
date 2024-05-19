package com.projects.book.store.servicesImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projects.book.store.constants.Messages;
import com.projects.book.store.dto.BookDTO;
import com.projects.book.store.dto.BookResponseDTO;
import com.projects.book.store.exception.NotFound;
import com.projects.book.store.model.Book;
import com.projects.book.store.repository.BookRepository;
import com.projects.book.store.services.BookService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ResponseEntity<?> findAll(@NotEmpty @NotBlank Integer pageSize, @NotEmpty @NotBlank Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Book> booksPage = bookRepository.findAll(pageRequest);

        Page<BookDTO> bookDTOPage = booksPage.map(book -> mapper.map(book, BookDTO.class));

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setPageNumber(bookDTOPage.getNumber());
        responseDTO.setPageSize(bookDTOPage.getSize());
        responseDTO.setBooks(bookDTOPage.getContent());

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty())
            throw new NotFound(Messages.BOOK_NOT_FOUND);
        Book book = optionalBook.get();
        BookDTO bookDTO = mapper.map(book, BookDTO.class);
        return ResponseEntity.ok(bookDTO);
    }

    @Override
    public ResponseEntity<?> addBook(BookDTO book) {
        Book bookSaving = mapper.map(book, Book.class);
        Book savedBook = bookRepository.save(bookSaving);
        BookDTO responseBookDTO = mapper.map(savedBook, BookDTO.class);
        return ResponseEntity.ok(responseBookDTO);
    }

    @Override
    public ResponseEntity<?> updateBook(BookDTO book) {
        Optional<Book> bookToUpdate = bookRepository.findById(book.getId());
        if (bookToUpdate.isEmpty())
            throw new NotFound(Messages.BOOK_NOT_FOUND);
        Book bookUpdated = bookToUpdate.get();
        bookUpdated.setIsbn(book.getIsbn());
        bookUpdated.setTitle(book.getTitle());
        bookUpdated.setAuthor(book.getAuthor());
        bookUpdated.setPrice(book.getPrice());
        bookUpdated.setPublicationYear(book.getPublicationYear());
        BookDTO responseBookDTO = mapper.map(bookUpdated, BookDTO.class);
        return ResponseEntity.ok(responseBookDTO);

    }

}
