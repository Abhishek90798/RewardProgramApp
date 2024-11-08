package com.retailer.rewardprogram.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.rewardprogram.model.Transaction;

/**
 * Repository interface for managing {@link Transaction} entities. Extends
 * {@link JpaRepository} to provide basic CRUD operations. Includes a custom
 * method to find transactions by customer ID.
 */
public interface ITransactionsRepository extends JpaRepository<Transaction, Long> {

	/**
	 * Finds all transactions for a given customer ID.
	 * 
	 * @param customerId
	 * @return list of entities associated with the given customer ID
	 */
	List<Transaction> findByCustomerId(Long customerId);
}
