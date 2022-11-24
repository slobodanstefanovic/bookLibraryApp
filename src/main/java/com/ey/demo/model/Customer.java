package com.ey.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Pattern(message = "can contains only letters", regexp = "^[a-zA-Z\\\\s]*$")
	@NotBlank(message = "First name may not be empty")
	@Column(name = "firstName", length = 50, nullable = false)
	private String firstName;

	@Pattern(message = "can contains only letters", regexp = "^[a-zA-Z\\\\s]*$")
	@NotBlank(message = "Last name may not be empty")
	@Column(name = "lastName", length = 50, nullable = false)
	private String lastName;

	public Customer() {

	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
