package com.retailer.rewardprogram.exceptions;

/**
 * Exception to be thrown when a customer is not found.
 */
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
