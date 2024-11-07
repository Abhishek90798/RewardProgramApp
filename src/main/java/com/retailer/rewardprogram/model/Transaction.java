package com.retailer.rewardprogram.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a transaction in the rewards program.
 * Contains customer ID, transaction amount, and transaction date.
 */
@Entity
@Table(name="transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="customer_id")
	private Long customerId;
	
	private double amount;
	
	private LocalDate date;
	
	/**
     * Default constructor for the Transaction entity.
     */
	public Transaction() {
		super();
	}
	
	/**
	 * 
	 * @param id the transaction ID
     * @param customerId the customer ID associated with the transaction
     * @param amount the transaction amount
     * @param date the date of the transaction
	 */
	public Transaction(Long id, Long customerId, double amount, LocalDate date) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.amount = amount;
		this.date = date;
	}
	
	/**
     * Returns the transaction ID.
     * 
     * @return transaction ID
     */
	public Long getId() {
        return id;
    }

	/**
     * Returns the customer ID associated with the transaction.
     * 
     * @return customer ID
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Returns the transaction amount.
     * 
     * @return transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the date of the transaction.
     * 
     * @return transaction date
     */
    public LocalDate getDate() {
        return date;
    }
	
}
