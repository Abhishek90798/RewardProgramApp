package com.retailer.rewardprogram.exceptions;

/**
 * Custom exception class to handle InvalidCustomerId errors. This exception can be
 * thrown when the input data is invalid or incorrect.
 */
public class InvalidCustomerId extends RuntimeException {

	/**
	 * Constructor for InvalidCustomerId with a custom error message.
	 * 
	 * @param message Detailed message explaining the reason for the exception.
	 */
	public InvalidCustomerId(String message) {
		super(message); 
	}
}
