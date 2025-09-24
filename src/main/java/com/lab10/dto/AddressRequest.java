package com.lab10.dto;

public record AddressRequest(
	String line1, 
	String line2, 
	String city,
	String postalCode, 
	String state,
	String country)
{

}
