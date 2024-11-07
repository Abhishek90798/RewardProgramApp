package com.retailer.rewardprogram.dto;

/**
 * Represents the reward points earned by a customer in a specific month.
 */
public class RewardInMonth {
	private String month;
	private int amount;
	
	/**
     * Constructs a RewardInMonth object with the given month and amount.
     * 
     * @param month the month for which the rewards are calculated
     * @param amount the reward points earned in that month
     */
	public RewardInMonth(String month, int amount) {
		
		this.month = month;
		this.amount = amount;
	}
	
	/**
     * Returns the month for which the rewards are calculated.
     * 
     * @return the month
     */
	public String getMonth() {
		return month;
	}
	
	/**
     * Returns the reward points earned in the month.
     * 
     * @return the reward amount
     */
	public int getAmount() {
		return amount;
	}
	
	

}
