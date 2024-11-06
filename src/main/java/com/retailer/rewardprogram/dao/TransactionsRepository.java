package com.retailer.rewardprogram.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.rewardprogram.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findByCustomerId(Long customerId);
}
