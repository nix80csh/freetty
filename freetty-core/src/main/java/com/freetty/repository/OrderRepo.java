package com.freetty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

}
