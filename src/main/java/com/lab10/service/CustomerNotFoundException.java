package com.lab10.service;

public class CustomerNotFoundException extends RuntimeException{
	public CustomerNotFoundException(Long id) {
		super("Cannot find this id :  "+id);
	}
}
