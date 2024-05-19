package com.projects.book.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.book.store.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
