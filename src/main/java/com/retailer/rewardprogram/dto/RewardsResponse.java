package com.retailer.rewardprogram.dto;

import java.util.ArrayList;

/**
 * Represents the reward points information for a customer. This includes
 * monthly points and the total points earned.
 */
public class RewardsResponse {

	private Long customerId;
	private ArrayList<RewardInMonth> monthlyPoints;
	private int totalPoints;

	/**
	 * Constructs a RewardsResponse object with the provided customer ID, monthly
	 * points, and total points.
	 * 
	 * @param monthlyPoints the list of reward points earned by the customer for
	 *                      each month
	 * @param totalPoints   the total reward points earned by the customer
	 * @param customerId    the unique identifier for the customer
	 */
	public RewardsResponse(ArrayList<RewardInMonth> monthlyPoints, int totalPoints, Long customerId) {
		this.monthlyPoints = monthlyPoints;
		this.totalPoints = totalPoints;
		this.customerId = customerId;
	}

	/**
	 * Returns the list of reward points earned by the customer for each month.
	 * 
	 * @return the list of monthly reward points
	 */
	public ArrayList<RewardInMonth> getMonthlyPoints() {
		return monthlyPoints;
	}

	/**
	 * Returns the total reward points earned by the customer.
	 * 
	 * @return the total reward points
	 */
	public int getTotalPoints() {
		return totalPoints;
	}

	/**
	 * Returns the unique identifier for the customer.
	 * 
	 * @return the customer ID
	 */
	public Long getCustomerId() {
		return customerId;
	}
}
