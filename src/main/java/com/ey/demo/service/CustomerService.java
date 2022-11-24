package com.ey.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ey.demo.model.Customer;

public interface CustomerService {

	Customer saveCustomer(Customer customer);

	List<Customer> findAll();

	Customer findById(Long id);

	Customer update(Customer customer, Long id);

	boolean delete(Long id);

	List<Customer> saveCustomersFromCsv(MultipartFile multipartFile);

}
