package com.retailer.rewardprogram.service;

import java.util.List;

import com.retailer.rewardprogram.dto.RewardsResponse;

/**
 * Interface for managing reward calculations for customers. It provides methods
 * to calculate reward points based on transactions.
 */
public interface IRewardService {

	/**
	 * Calculates the reward points for a specific customer based on their
	 * transactions.
	 *
	 * @param customerId the unique identifier of the customer
	 * @return a RewardsResponse object containing the monthly points and total
	 *         points for the customer
	 */
	public RewardsResponse calculateRewards(Long customerId);

	/**
	 * Calculates the reward points for all customers based on their transactions.
	 *
	 * @return a list of RewardsResponse objects, each containing the monthly points
	 *         and total points for each customer
	 */
	public List<RewardsResponse> calculateRewardsForAll();
}
