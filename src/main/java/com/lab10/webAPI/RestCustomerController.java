package com.lab10.webAPI;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab10.*;
import com.lab10.dto.*;
import com.lab10.entity.*;
import com.lab10.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")

public class RestCustomerController {

	@Autowired
	private CustomerService custService;
	
	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getCustomer(){
		List<CustomerResponse> customers = custService.getCustomerList();
		return new ResponseEntity<>(customers,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	//SELECT * FROM customer WHERE id = ... ;
	public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id){
		CustomerResponse customer = custService.getOneCustomerById(id);
		return new ResponseEntity<>(customer,HttpStatus.OK);
	}
	
	@PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerRequest cust,BindingResult br){
        if(br.hasErrors()) {
            Map<String,Object> err = new LinkedHashMap<>();
            err.put("status", 400);
            err.put("error", "Bad Request");
            err.put("message","Validation failed");
            err.put("fieldErrors", br.getFieldErrors().stream()
                    .map(fe->Map.of(
                            "field",fe.getField(),
                            "message",fe.getDefaultMessage(),
                            "rejectedValue",fe.getRejectedValue())).toList());
            return ResponseEntity.badRequest().body(err);
        }
        else {
                CustomerResponse response = custService.save(cust);
                return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,@RequestBody @Valid CustomerRequest cust,BindingResult br){
		if(br.hasErrors()) {
            Map<String,Object> err = new LinkedHashMap<>();
            err.put("status", 400);
            err.put("error", "Bad Request");
            err.put("message","Validation failed");
            err.put("fieldErrors", br.getFieldErrors().stream()
                    .map(fe->Map.of(
                            "field",fe.getField(),
                            "message",fe.getDefaultMessage(),
                            "rejectedValue",fe.getRejectedValue())).toList());
            return ResponseEntity.badRequest().body(err);
        }
        else {
        	 return new ResponseEntity<>(custService.updateCustomer(id,cust),HttpStatus.OK);
        }
       
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        CustomerResponse custdel = custService.getOneCustomerById(id);
        if(custdel != null) {
            custService.deleteCustomerById(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Customer doesn't Exist",HttpStatus.NOT_FOUND);
    }
    
	
}
