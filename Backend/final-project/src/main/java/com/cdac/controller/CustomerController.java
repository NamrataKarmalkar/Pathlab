package com.cdac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.RegistrationStatus;
import com.cdac.dto.Status;
import com.cdac.entity.Customer;
import com.cdac.exception.CustomerServiceException;
import com.cdac.service.CustomerService;
import com.cdac.dto.LoginDetails;
import com.cdac.dto.LoginStatus;
import java.util.List;
import java.util.Collections;

@RestController
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	
	@PostMapping("/register")
	public RegistrationStatus register(@RequestBody Customer customer) {
		try {
			int id = customerService.register(customer);
			
			RegistrationStatus status = new RegistrationStatus();
			status.setStatus(true);
			status.setStatusMessage("Customer registered successfully!");
			status.setCustomerId(id);
			return status;
		}
		catch (CustomerServiceException e) {
			RegistrationStatus status = new RegistrationStatus();
			status.setStatus(false);
			status.setStatusMessage(e.getMessage());
			return status;			
		}
	}
	@GetMapping("/getAll")
    public List<Customer> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return customers;
        } catch (CustomerServiceException e) {
            // Handle exception and return appropriate response
            // For simplicity, you can return an empty list or handle it as needed
            return Collections.emptyList();
        }
    }
	
	@DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
        try {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (CustomerServiceException e) {
            // Handle exception and return appropriate response
            // For simplicity, you can return a bad request with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

	@PutMapping("/update/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable int customerId, @RequestBody Customer updatedCustomer) {
        try {
            customerService.updateCustomer(customerId, updatedCustomer);
            return ResponseEntity.ok("Customer updated successfully");
        } catch (CustomerServiceException e) {
            // Handle exception and return appropriate response
            // For simplicity, you can return a bad request with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


@PostMapping("/login")
public Status login(@RequestBody LoginDetails loginDetails) {
	try {
		Customer customer = customerService.login(loginDetails.getEmail(), loginDetails.getPassword());
		LoginStatus status = new LoginStatus();
		status.setStatus(true);
		status.setMessageIfAny("Login successful!");
		status.setCustomerId(customer.getId());
		status.setName(customer.getName());
		//status.setCustomer(customer);
		return status;
	}
	catch (CustomerServiceException e) {
		Status status = new Status();
		status.setStatus(false);
		status.setMessageIfAny(e.getMessage());
		return status;
	}
}

	@GetMapping("/delete/{id}")
	public Customer fetchById(@PathVariable int id) {
		return customerService.fetchById(id);
		
	}

}
