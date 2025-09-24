package com.lab10.dto;

public record AddressResponse(
	Long id,
	String line1,
	String line2,
	String city,
	String state,
	String postalCode,
	String country
)
{}
