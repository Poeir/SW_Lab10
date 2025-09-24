package com.lab10.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.lab10.dto.AddressRequest;
import com.lab10.dto.AddressResponse;
import com.lab10.dto.CustomerRequest;
import com.lab10.dto.CustomerResponse;
import com.lab10.entity.Address;
import com.lab10.entity.Customer;
import com.lab10.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    // ------------------- Mapping -------------------
    private CustomerResponse mapToResponse(Customer c) {
        AddressResponse addrResp = null;
        Address a = c.getAddress();
        if (a != null) {
            addrResp = new AddressResponse(
                a.getId(),
                a.getLine1(),
                a.getLine2(),
                a.getCity(),
                a.getState(),
                a.getPostalCode(),
                a.getCountry()
            );
        }
        return new CustomerResponse(c.getId(), c.getName(), c.getEmail(), addrResp);
    }

    private void mapAddressFromRequest(Customer c, AddressRequest req) {
        Address a = c.getAddress();
        if (a == null) {
            a = new Address();
            a.setCustomer(c);
            c.setAddress(a);
        }
        a.setLine1(req.line1());        // ใช้ .line1() แทน getLine1()
        a.setLine2(req.line2());
        a.setCity(req.city());
        a.setState(req.state());
        a.setPostalCode(req.postalCode());
        a.setCountry(req.country());
    }


    // ------------------- CREATE -------------------
    @Transactional
    public CustomerResponse create(CustomerRequest req) {
        Customer c = new Customer();
        c.setName(req.getName());
        c.setEmail(req.getEmail());
        
        if (req.getAddress() != null) {
            mapAddressFromRequest(c, req.getAddress());
        }
        Customer saved = repo.save(c);
        return mapToResponse(saved);
    }

    // ------------------- READ -------------------
    @Transactional(readOnly = true)
    public CustomerResponse get(Long id) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer %d not found".formatted(id)
                ));
        return mapToResponse(c);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return repo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ------------------- UPDATE -------------------
    @Transactional
    public CustomerResponse update(Long id, CustomerRequest req) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer %d not found".formatted(id)
                ));
        c.setName(req.getName());
        c.setEmail(req.getEmail());
        if (req.getAddress() != null) {
            mapAddressFromRequest(c, req.getAddress());
        }
        Customer updated = repo.save(c);
        return mapToResponse(updated);
    }

    // ------------------- DELETE -------------------
    @Transactional
    public void delete(Long id) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer %d not found".formatted(id)
                ));
        repo.delete(c);
    }
}
