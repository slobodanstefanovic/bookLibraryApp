package com.ey.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
