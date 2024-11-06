package com.retailer.rewardprogram.response;

public class RewardInMonth {
	private String month;
	private int amount;
	public RewardInMonth(String month, int amount) {
		
		this.month = month;
		this.amount = amount;
	}
	public String getMonth() {
		return month;
	}
	public int getAmount() {
		return amount;
	}
	
	

}
