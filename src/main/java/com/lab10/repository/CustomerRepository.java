package com.lab10.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lab10.entity.*;

public interface CustomerRepository extends JpaRepository<Customer,Long> {}
