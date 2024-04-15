package com.cdac.service;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.entity.Customer;
import com.cdac.exception.CustomerServiceException;
import com.cdac.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public int register(Customer customer) {
		//suppose we need to check if this customer has already registered before
		Optional<Customer> customerCheck = customerRepository.findByEmail(customer.getEmail());
		if(customerCheck.isEmpty()) {
			Customer savedCustomer = customerRepository.save(customer);
			return savedCustomer.getId();
		}
		else
			throw new CustomerServiceException("Customer already registered!");
	}

	public Customer login(String email, String password) {
		
		Optional<Customer> customer = customerRepository.findByEmailAndPassword(email, password);
		if(customer.isPresent())
			return customer.get();
		else
			throw new CustomerServiceException("Invalid Email/Password");
	
		}

	public Customer fetchById(int id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if(customer.isPresent())
			return customer.get();
		else
			throw new CustomerServiceException("Customer with id " + id + " does not exist!");
		
	}

public List<Customer> getAllCustomers() {
    try {
        return customerRepository.findAll();
    } catch (Exception e) {
        throw new CustomerServiceException("Error fetching all customers.", e);
    }
}

public void deleteCustomer(int customerId) {
   
    customerRepository.deleteById(customerId);
}


public void updateCustomer(int customerId, Customer updatedCustomer) {
   
    Optional<Customer> existingCustomer = customerRepository.findById(customerId);
    if (existingCustomer.isPresent()) {
        Customer customerToUpdate = existingCustomer.get();
        // Update common fields
        customerToUpdate.setName(updatedCustomer.getName());
        customerToUpdate.setEmail(updatedCustomer.getEmail());
        customerToUpdate.setPassword(updatedCustomer.getPassword());
        // Update additional fields
        customerToUpdate.setAge(updatedCustomer.getAge());
        customerToUpdate.setCity(updatedCustomer.getCity());
        customerToUpdate.setNumber(updatedCustomer.getNumber());
        customerToUpdate.setTime(updatedCustomer.getTime());
        
        // Update other fields as needed
        customerRepository.save(customerToUpdate);
    } else {
        throw new CustomerServiceException("Customer with id " + customerId + " does not exist!");
    }
}
}