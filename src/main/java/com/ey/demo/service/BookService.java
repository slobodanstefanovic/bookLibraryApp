package com.ey.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ey.demo.model.Book;

public interface BookService {

	Book saveBook(Book book);

	List<Book> findAll();

	Book findById(Long id);

	Book update(Book book, Long id);

	boolean delete(Long id);

	List<Book> saveBooksFromCsv(MultipartFile multipartFile);

}
