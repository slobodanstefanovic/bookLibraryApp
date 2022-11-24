package com.ey.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ey.demo.enumeration.Genre;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ISBN", nullable = false, unique = true)
	private String ISBN;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "num_pages", nullable = false)
	private int numPages;

	@Column(name = "genre", nullable = false)
	@Enumerated(EnumType.STRING)
	private Genre genre;

	public Book() {
	}

	public Book(String iSBN, String name, String author, int numPages, Genre genre) {
		ISBN = iSBN;
		this.name = name;
		this.author = author;
		this.numPages = numPages;
		this.genre = genre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNumPages() {
		return numPages;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", ISBN=" + ISBN + ", name=" + name + ", author=" + author + ", numPages=" + numPages
				+ ", genre=" + genre + "]";
	}

}