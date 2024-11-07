package com.retailer.rewardprogram.exceptions;

/**
 * Exception to be thrown when a customer is not found.
 */
public class NoTransactionFoundException extends RuntimeException {

    public NoTransactionFoundException(String message) {
        super(message);
    }
}
