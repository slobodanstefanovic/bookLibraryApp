package com.ey.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
