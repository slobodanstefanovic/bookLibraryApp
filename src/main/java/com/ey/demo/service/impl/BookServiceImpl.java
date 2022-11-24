package com.ey.demo.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ey.demo.exception.ResourceNotFoundException;
import com.ey.demo.helper.CSVHelper;
import com.ey.demo.model.Book;
import com.ey.demo.repository.BookRepository;
import com.ey.demo.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Transactional
	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public Book findById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Book.class.getName(), "Id", id.toString()));
	}

	@Transactional
	@Override
	public Book update(Book book, Long id) {
		Book existing = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Book.class.getName(), "Id", id.toString()));
		existing.setName(book.getName());
		existing.setAuthor(book.getAuthor());
		existing.setNumPages(book.getNumPages());
		existing.setGenre(book.getGenre());
		existing.setISBN(book.getISBN());
		bookRepository.save(existing);

		return existing;
	}

	@Transactional
	@Override
	public boolean delete(Long id) {
		if (bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
			return true;
		} else {
			return false;

		}
	}

	@Override
	public List<Book> saveBooksFromCsv(MultipartFile multipartFile) {
		if (CSVHelper.hasCSVFormat(multipartFile)) {
			try {
				List<Book> books = CSVHelper.csvToBooks(multipartFile.getInputStream());
				bookRepository.saveAll(books);
			} catch (IOException e) {
				throw new RuntimeException("fail to store BOOKS csv data: " + e.getMessage());
			}
		}
		return null;
	}
}
