package com.ey.demo.service.impl;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ey.demo.exception.ResourceNotFoundException;
import com.ey.demo.helper.CSVHelper;
import com.ey.demo.model.Customer;
import com.ey.demo.repository.CustomerRepository;
import com.ey.demo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Transactional
	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);

	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer findById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Customer.class.getName(), "Id", id.toString()));
	}

	@Transactional
	@Override
	public Customer update(Customer customer, Long id) {
		Customer existing = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Customer.class.getName(), "Id", id.toString()));
		existing.setFirstName(customer.getFirstName());
		existing.setLastName(customer.getLastName());
		customerRepository.save(existing);

		return existing;

	}

	@Transactional
	@Override
	public boolean delete(Long id) {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Customer> saveCustomersFromCsv(MultipartFile multipartFile) {
		if (CSVHelper.hasCSVFormat(multipartFile)) {
			try {
				List<Customer> customers = CSVHelper.csvToCustomers(multipartFile.getInputStream());
				customerRepository.saveAll(customers);
				return customers;
			} catch (IOException e) {
				throw new RuntimeException("fail to store CUSTOMER csv data: " + e.getMessage());
			}
		}
		return null;
	}
}
