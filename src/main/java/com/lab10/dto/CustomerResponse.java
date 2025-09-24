package com.lab10.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerResponse(
			@NotNull Long id,
			@NotBlank String name,
			@Email String email,
			AddressResponse address
){}
	
