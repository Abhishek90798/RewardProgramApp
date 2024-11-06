package com.retailer.rewardprogram.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	public Transaction() {
		super();
	}

	public Transaction(Long id, Long customerId, double amount, LocalDate date) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.amount = amount;
		this.date = date;
	}
	
	public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
	
}
