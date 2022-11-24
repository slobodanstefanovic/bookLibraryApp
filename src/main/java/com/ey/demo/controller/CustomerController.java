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
import com.ey.demo.model.Customer;
import com.ey.demo.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
		return new ResponseEntity<Customer>(customerService.saveCustomer(customer), HttpStatus.CREATED);
	}

	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long customerId) {
		return new ResponseEntity<Customer>(customerService.findById(customerId), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long customerId,
			@RequestBody Customer customer) {
		return new ResponseEntity<Customer>(customerService.update(customer, customerId), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteCustomer(@PathVariable("id") Long customerId) {
		return new ResponseEntity<Boolean>(customerService.delete(customerId), HttpStatus.OK);
	}

	@PostMapping("/upload")
	public ResponseEntity<Customer> uploadFile(@RequestParam("customersCSV") MultipartFile multipartFile) {
		if (multipartFile != null && CSVHelper.hasCSVFormat(multipartFile)) {
			List<Customer> uploadCustomers = customerService.saveCustomersFromCsv(multipartFile);
			return uploadCustomers != null ? (new ResponseEntity<Customer>(HttpStatus.OK))
					: (new ResponseEntity<>(HttpStatus.BAD_REQUEST));
		} else {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}