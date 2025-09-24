package com.lab10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab10.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {}

