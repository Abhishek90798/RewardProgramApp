package com.retailer.rewardprogram.response;

import java.util.ArrayList;

public class RewardsResponse {
	private Long customerId;
	private ArrayList<RewardInMonth> monthlyPoints;
	private int totalPoints;
	public RewardsResponse(ArrayList<RewardInMonth> monthlyPoints, int totalPoints,Long customerId) {

		this.monthlyPoints = monthlyPoints;
		this.totalPoints = totalPoints;
		this.customerId=customerId;
	}
	public ArrayList<RewardInMonth> getMonthlyPoints() {
		return monthlyPoints;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	public Long getCustomerId() {
		return customerId;
	}
	
	
}
