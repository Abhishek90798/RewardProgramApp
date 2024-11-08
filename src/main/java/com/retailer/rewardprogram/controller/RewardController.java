package com.retailer.rewardprogram.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardprogram.dto.RewardsResponse;
import com.retailer.rewardprogram.exceptions.NoTransactionFoundException;
import com.retailer.rewardprogram.service.IRewardService;

/**
 * REST controller for handling and retrieving rewards points request.
 */
@RestController
@RequestMapping("/api/rewards")
@Validated
public class RewardController {

	private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

	@Autowired
	private IRewardService rewardService;

	/**
	 * Retrieves the reward points for a specific customer by their ID.
	 *
	 * @param customerId the ID of the customer whose reward points are to be
	 *                   fetched
	 * @return ResponseEntity containing RewardsResponse object with the reward
	 *         points, or a NOT_FOUND status if no reward points are available for
	 *         the given customer ID.
	 */
	@GetMapping("/{customerId}")
	public ResponseEntity<RewardsResponse> getRewards(@PathVariable @NotNull Long customerId) {
		logger.info("Fetching reward points for customer with ID: {}", customerId);

		RewardsResponse rewardsResponse = rewardService.calculateRewards(customerId);

		if (rewardsResponse.getMonthlyPoints().isEmpty()) {
			logger.warn("No reward points found for customer ID: {}", customerId);
			throw new NoTransactionFoundException("No transactions found for customer ID: " + customerId);
		}

		logger.info("Successfully fetched reward points for customer ID: {}", customerId);
		return new ResponseEntity<>(rewardsResponse, HttpStatus.OK);
	}

	/**
	 * Retrieves the reward points for all customers.
	 *
	 * @return ResponseEntity containing List of RewardsResponse objects with the
	 *         reward points, and HTTP status code OK
	 */
	@GetMapping("/all")
	public ResponseEntity<List<RewardsResponse>> getAllRewards() {
		logger.info("Fetching reward points for all customers");

		List<RewardsResponse> allCustomerRewards = rewardService.calculateRewardsForAll();

		logger.info("Successfully fetched rewards for all customers, total customers: {}", allCustomerRewards.size());
		return new ResponseEntity<>(allCustomerRewards, HttpStatus.OK);
	}
}
