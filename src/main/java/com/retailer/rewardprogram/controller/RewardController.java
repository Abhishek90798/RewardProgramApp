package com.retailer.rewardprogram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardprogram.response.RewardsResponse;
import com.retailer.rewardprogram.service.RewardService;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {
	
	@Autowired
	private RewardService rewardService;
	
	@GetMapping("/{customerId}")
	public ResponseEntity<RewardsResponse> getRewards(@PathVariable Long customerId){
		RewardsResponse rewardsResponse=rewardService.calculateRewards(customerId);
		if(rewardsResponse.getMonthlyPoints().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rewardsResponse,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<RewardsResponse>> getAllRewards(){
		List<RewardsResponse> allCustomerReward=rewardService.calculateRewardsForAll();
		return new ResponseEntity<>(allCustomerReward,HttpStatus.OK);
	}
}
