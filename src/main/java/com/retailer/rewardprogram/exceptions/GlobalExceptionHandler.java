package com.retailer.rewardprogram.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to handle various exceptions globally in the
 * application. This class provides centralized handling of different exceptions
 * and sends appropriate HTTP responses to the client.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handles BadRequestException and returns a 400 Bad Request status.
	 * 
	 * @param badRequestException the exception that was thrown
	 * @return ResponseEntity with the error message and HTTP status
	 */
	@ExceptionHandler(InvalidCustomerId.class)
	public ResponseEntity<String> handleBadRequest(InvalidCustomerId invalidCustomerId) {
		logger.error("Invalid Customer Id: {}", invalidCustomerId.getMessage());
		return new ResponseEntity<>(invalidCustomerId.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles NoTransactionFoundException and returns a 404 Not Found status.
	 * 
	 * @param noTransactionFoundException the exception that was thrown
	 * @return ResponseEntity with the error message and HTTP status
	 */
	@ExceptionHandler(NoTransactionFoundException.class)
	public ResponseEntity<String> handleNoTransactionFound(NoTransactionFoundException noTransactionFoundException) {
		logger.error("No transactions found: {}", noTransactionFoundException.getMessage());
		return new ResponseEntity<>(noTransactionFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles validation errors that occur during request parameter validation.
	 * Returns a 400 Bad Request status with a map of field errors.
	 * 
	 * @param methodArgumentNotValidException the exception that was thrown
	 * @return ResponseEntity with a map of field error messages and HTTP status
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(
			MethodArgumentNotValidException methodArgumentNotValidException) {
		Map<String, String> errors = new HashMap<>();
		methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles all other general exceptions and returns a 500 Internal Server Error
	 * status.
	 * 
	 * @param exception the exception that was thrown
	 * @return ResponseEntity with the error message and HTTP status
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception exception) {
		logger.error("Internal Server Error: {}", exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
