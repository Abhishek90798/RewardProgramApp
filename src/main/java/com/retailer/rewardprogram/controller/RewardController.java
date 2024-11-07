package com.retailer.rewardprogram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardprogram.dto.RewardsResponse;
import com.retailer.rewardprogram.service.IRewardService;

/**
 * REST controller for handling and retrieving rewards points request.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController {
	
	@Autowired
	private IRewardService rewardService;
	
	/**
     * Retrieves the reward points for a specific customer by their ID.
     *
     * @param customerId the ID of the customer whose reward points are to be fetched
     * @return ResponseEntity containing RewardsResponse object with the reward points, 
     * or a NOT_FOUND status if no reward points are available for the given customer ID.
     */
	@GetMapping("/{customerId}")
	public ResponseEntity<RewardsResponse> getRewards(@PathVariable Long customerId){
		RewardsResponse rewardsResponse=rewardService.calculateRewards(customerId);
		if(rewardsResponse.getMonthlyPoints().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rewardsResponse,HttpStatus.OK);
	}
	
	/**
     * Retrieves the reward points for a all customers.
     *
     * @return ResponseEntity containing List of RewardsResponse object with the reward points, 
     * and HTTP status code OK
     */
	@GetMapping("/all")
	public ResponseEntity<List<RewardsResponse>> getAllRewards(){
		List<RewardsResponse> allCustomerReward=rewardService.calculateRewardsForAll();
		return new ResponseEntity<>(allCustomerReward,HttpStatus.OK);
	}
}
