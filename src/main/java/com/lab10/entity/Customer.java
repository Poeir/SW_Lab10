package com.lab10.entity;

import com.lab10.entity.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="customer")

public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String name;
	@Column
	private String email;
	
	@OneToOne(mappedBy = "customer",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private Address address;
	
	
	//===== Constructor ===== 
	public Customer() {}
	public Customer(Long id , String name ,String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	//===== GETTER SETTER =====
	//- Email
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	//- Id
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	//- Name
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
	    if (address == null) {
	        // กรณี assign ค่า null → ต้องตัดความสัมพันธ์เดิม
	        if (this.address != null) 
	            this.address.setCustomer(null);  // ตัด customer ออกจาก address เดิม
	        this.address = null;                 // clear ค่าใน customer เอง
	    } else {
	        // กรณี assign address ใหม่
	        address.setCustomer(this);           // ตั้งค่าฝั่ง Address ให้ชี้มาที่ Customer
	        this.address = address;              // ตั้งค่าฝั่ง Customer ให้ชี้มาที่ Address
	    }
	}
	

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
}
