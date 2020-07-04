package com.freetty.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.Customer;


public interface CustomerRepo extends JpaRepository<Customer, Integer>{	
	public Customer findByEmail(String email);
	public Customer findByMobile(String mobile);
	public int countByEmail(String email);
	
}