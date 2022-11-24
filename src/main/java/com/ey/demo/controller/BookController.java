package com.ey.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ey.demo.helper.CSVHelper;
import com.ey.demo.model.Book;
import com.ey.demo.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		return new ResponseEntity<Book>(bookService.saveBook(book), HttpStatus.CREATED);
	}

	@GetMapping
	public List<Book> getAllbooks() {
		return bookService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {
		return new ResponseEntity<Book>(bookService.findById(bookId), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long bookId, @RequestBody Book book) {
		return new ResponseEntity<Book>(bookService.update(book, bookId), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteBook(@PathVariable("id") Long bookId) {
		return new ResponseEntity<Boolean>(bookService.delete(bookId), HttpStatus.OK);
	}

	@PostMapping("/upload")
	public ResponseEntity<Book> uploadFile(@RequestParam("booksCSV") MultipartFile multipartFile) {
		if (multipartFile != null && CSVHelper.hasCSVFormat(multipartFile)) {
			List<Book> addBooks = bookService.saveBooksFromCsv(multipartFile);
			return addBooks != null ? (new ResponseEntity<Book>(HttpStatus.OK))
					: (new ResponseEntity<>(HttpStatus.BAD_REQUEST));

		} else {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
