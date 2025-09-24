package com.lab10.webAPI;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.lab10.dto.*;
import com.lab10.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class RestCustomerController {

    @Autowired
    private CustomerService custService;

    // ------------------- GET ALL -------------------
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = custService.getAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse customer = custService.get(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // ------------------- CREATE -------------------
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerRequest cust, BindingResult br) {
        if (br.hasErrors()) {
            Map<String, Object> err = new LinkedHashMap<>();
            err.put("status", 400);
            err.put("error", "Bad Request");
            err.put("message", "Validation failed");
            err.put("fieldErrors", br.getFieldErrors().stream()
                    .map(fe -> Map.of(
                            "field", fe.getField(),
                            "message", fe.getDefaultMessage(),
                            "rejectedValue", fe.getRejectedValue()))
                    .collect(Collectors.toList()));
            return ResponseEntity.badRequest().body(err);
        } else {
            CustomerResponse response = custService.create(cust);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 CREATED
        }
    }

    // ------------------- UPDATE -------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest cust, BindingResult br) {
        if (br.hasErrors()) {
            Map<String, Object> err = new LinkedHashMap<>();
            err.put("status", 400);
            err.put("error", "Bad Request");
            err.put("message", "Validation failed");
            err.put("fieldErrors", br.getFieldErrors().stream()
                    .map(fe -> Map.of(
                            "field", fe.getField(),
                            "message", fe.getDefaultMessage(),
                            "rejectedValue", fe.getRejectedValue()))
                    .collect(Collectors.toList()));
            return ResponseEntity.badRequest().body(err);
        } else {
            CustomerResponse response = custService.update(id, cust);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        custService.delete(id); // ถ้าไม่มี customer จะ throw NOT_FOUND
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
