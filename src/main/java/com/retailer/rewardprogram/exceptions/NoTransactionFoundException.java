package com.retailer.rewardprogram.exceptions;

/**
 * Exception to be thrown when a customer is not found.
 */
public class NoTransactionFoundException extends RuntimeException {
	/**
	 * 
	 * Constructor for NoTransactionFoundException with a custom error message.
	 * @param message Detailed message explaining the reason for the exception.
	 */
	public NoTransactionFoundException(String message) {
		super(message);
	}
}
