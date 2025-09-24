package com.lab10.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab10.dto.CustomerRequest;
import com.lab10.dto.CustomerResponse;
import com.lab10.entity.Address;
import com.lab10.entity.Customer;
import com.lab10.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository custRepo;
	public CustomerService(CustomerRepository repo) {
		this.repo = repo;
	}
	
	private CustomerResponse mapToResponse(Customer cust) {
		return new CustomerResponse(cust.getId(),cust.getName(),cust.getEmail());
	}
	private Customer toEntity(CustomerRequest request) {
		Customer customer = new Customer();
		customer.setName(request.getName());
		customer.setEmail(request.getEmail());
		return customer;
	}
	
	
	public List<CustomerResponse> getCustomerList(){
	    List<Customer> customers = (List<Customer>) custRepo.findAll();
	    
	    return customers.stream()
	        .map(c -> new CustomerResponse(c.getId(), c.getName(), c.getEmail()))  
	        .toList();
	}
	
	public CustomerResponse getOneCustomerById(Long id ) {
		Customer customer = custRepo.findById(id).get();
		return mapToResponse(customer);
	}
	

	public CustomerResponse save(CustomerRequest cust) {
		Customer customer = toEntity(cust);
		Address address = new Address();
		address.setLine1(customer.getEmail());
		custRepo.save(customer);
		return mapToResponse(customer);
		
	}
	
	public CustomerResponse addCustomer(CustomerRequest cust) {
		Customer customer = toEntity(cust);
		Customer custNew = custRepo.save(customer);
		return new CustomerResponse(
				custNew.getId(),
				custNew.getName(),
				custNew.getEmail()
				);
	}
	public CustomerResponse updateCustomer(Long id , CustomerRequest custNew) {
		Customer custExist = custRepo.findById(id).orElseThrow( () -> new CustomerNotFoundException(id));
		custExist.setName(custNew.getName());
		custExist.setEmail(custNew.getEmail());
		custRepo.save(custExist);
		return new CustomerResponse(
				custExist.getId(),
				custExist.getName(),
				custExist.getEmail());
	}
	
	public void deleteCustomerById(Long id) {
		Customer cust = custRepo.findById(id).orElseThrow( () -> new CustomerNotFoundException(id));
		custRepo.delete(cust);
	}
	
	
	
}
