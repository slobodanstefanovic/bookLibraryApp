package com.ey.demo.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.ey.demo.enumeration.Genre;
import com.ey.demo.model.Book;
import com.ey.demo.model.Customer;

public class CSVHelper {

	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile multipartFile) {
		if (!TYPE.equals((multipartFile.getContentType()))) {
			return false;
		} else {
			return true;
		}
	}

	public static List<Book> csvToBooks(InputStream inputStream) {
		List<Book> listOfBooks = new ArrayList<>();

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				if (validateBooksCsvFile(csvRecord)) {

				Genre genre = convertGenreFieldFromCsv(csvRecord.get("genre"));
				Book book = new Book(csvRecord.get("ISBN"), csvRecord.get("name"), csvRecord.get("author"),
						Integer.parseInt(csvRecord.get("numPages")), genre);
				listOfBooks.add(book);
			}
			}

		} catch (IOException e1) {
			throw new RuntimeException("fail to parse CSV file: " + e1.getMessage());
		}
		return listOfBooks;

	}

	private static boolean validateBooksCsvFile(CSVRecord csvRecord) {
		if (csvRecord.get("ISBN").isEmpty() && csvRecord.get("ISBN").matches("/^-?\\d+\\.?\\d*$/")
				&& csvRecord.get("name").isEmpty() && csvRecord.get("name").matches("^[a-zA-Z\\\\s]*$")
				&& csvRecord.get("author").isEmpty() && csvRecord.get("author").matches("^[a-zA-Z\\\\s]*$")
				&& csvRecord.get("numPages").isEmpty() && Integer.parseInt(csvRecord.get("numPages")) > 0
				&& csvRecord.get("genre").isEmpty() && csvRecord.get("genre").matches("^[a-zA-Z\\\\s]*$")) {
			return false;
		} else {
			return true;
		}
	}

	public static List<Customer> csvToCustomers(InputStream inputStream) {
		List<Customer> listOfCustomers = new ArrayList<>();

		try (BufferedReader filReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				CSVParser csvParser = new CSVParser(filReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				if (validateCustomerCsvFile(csvRecord)) {
				
				Customer customer = new Customer(csvRecord.get("firstName"), csvRecord.get("lastName"));
				listOfCustomers.add(customer);
			}
			}

		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
		return listOfCustomers;
	}

	private static boolean validateCustomerCsvFile(CSVRecord csvRecord) {
		if (csvRecord.get("firstName").isBlank() && csvRecord.get("firstName").matches("^[a-zA-Z\\\\s]*$")
				&& csvRecord.get("lastName").isBlank() && csvRecord.get("lastName").matches("^[a-zA-Z\\\\s]*$")) {
			return false;
		} else {
			return true;
	}
}
// HORROR, ACTION, COMEDY, ADVENTURE, BIOGRAPHY, CLASSIC, FANTASY, CRIME, PSYCHOLOGY, DRAMA
	private static Genre convertGenreFieldFromCsv(String genre) {
		switch (genre) {
		case "HORROR":
			return Genre.HORROR;
		case "ACTION":
			return Genre.ACTION;
		case "COMEDY":
			return Genre.COMEDY;
		case "ADVENTURE":
			return Genre.ADVENTURE;
		case "BIOGRAPHY":
			return Genre.BIOGRAPHY;
		case "CLASSIC":
			return Genre.CLASSIC;
		case "FANTASY":
			return Genre.FANTASY;
		case "CRIME":
			return Genre.CRIME;
		case "PSYCHOLOGY":
			return Genre.PSYCHOLOGY;
		case "DRAMA":
			return Genre.DRAMA;
		default:
			break;
		}
		return null;
	}

}
