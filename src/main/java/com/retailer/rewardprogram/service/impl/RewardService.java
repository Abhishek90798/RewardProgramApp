package com.retailer.rewardprogram.service.impl;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewardprogram.dao.ITransactionsRepository;
import com.retailer.rewardprogram.dto.RewardInMonth;
import com.retailer.rewardprogram.dto.RewardsResponse;
import com.retailer.rewardprogram.exceptions.InvalidCustomerId;
import com.retailer.rewardprogram.exceptions.NoTransactionFoundException;
import com.retailer.rewardprogram.model.Transaction;
import com.retailer.rewardprogram.service.IRewardService;

/**
 * Service class responsible for calculating reward points for customers based
 * on their transaction data.
 */
@Service
public class RewardService implements IRewardService {

	private static final Logger logger = LoggerFactory.getLogger(RewardService.class);

	@Autowired
	private ITransactionsRepository repository;

	/**
	 * Calculates the reward points for a specific customer based on their
	 * transactions in the last three months.
	 *
	 * @param customerId the unique identifier of the customer
	 * @return a RewardsResponse object containing the monthly points and total
	 *         points
	 */
	public RewardsResponse calculateRewards(Long customerId) {
		if (customerId <= 0L) {
			logger.error("Invalid customer ID: {}", customerId);
			throw new InvalidCustomerId("Customer ID must be a positive value greater than zero.");
		}
		logger.info("Calculating reward points for customer ID: {}", customerId);

		LocalDate today = LocalDate.now();
		List<String> lastThreeMonths = List.of(
				today.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")),
				today.minusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM")),
				today.minusMonths(3).format(DateTimeFormatter.ofPattern("yyyy-MM"))
		);

		LocalDate lastDayOfLastMonth = today.withDayOfMonth(1).minusDays(1);
		LocalDate firstDayOfThreeMonthsAgo = today.minusMonths(3).withDayOfMonth(1);

		List<Transaction> transactions = repository.findByCustomerId(customerId).stream()
				.filter(transaction -> !transaction.getDate().isBefore(firstDayOfThreeMonthsAgo)
						&& !transaction.getDate().isAfter(lastDayOfLastMonth))
				.collect(Collectors.toList());

		if (transactions.isEmpty()) {
			logger.error("No transactions found for customer ID: {}", customerId);
			throw new NoTransactionFoundException(
					"Customer with ID " + customerId + " has no transactions in last three months");
		}

		Map<String, Integer> monthlyPoints = transactions.stream()
				.collect(Collectors.groupingBy(
						transaction -> transaction.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM")),
						Collectors.summingInt(transaction -> calculatePoints(transaction.getAmount()))
				));

		int sum = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();
		ArrayList<RewardInMonth> list = new ArrayList<>();

		for (String month : lastThreeMonths) {
			if (monthlyPoints.containsKey(month)) {
				int monthNo = Integer.parseInt(month.split("-")[1]);
				list.add(new RewardInMonth(Month.of(monthNo).getDisplayName(TextStyle.FULL, Locale.ENGLISH),
						monthlyPoints.get(month)));
			}
		}

		logger.info("Successfully calculated rewards for customer ID: {} with total points: {}", customerId, sum);
		return new RewardsResponse(new ArrayList<>(list), sum, customerId);
	}

	/**
	 * Calculates the reward points for all customers based on their transactions in
	 * the last three months.
	 *
	 * @return a list of RewardsResponse objects for all customers
	 */
	public List<RewardsResponse> calculateRewardsForAll() {
		logger.info("Calculating rewards for all customers");

		List<Transaction> lastThreeMonthsTransactions = Arrays.asList(
				// Customer 1 transactions across three months
				new Transaction(1L, 101L, 120.0, LocalDate.of(2023, 7, 10)),
				new Transaction(2L, 101L, 75.0, LocalDate.of(2023, 8, 15)),
				new Transaction(3L, 101L, 150.0, LocalDate.of(2023, 9, 5)),
				new Transaction(4L, 101L, 200.0, LocalDate.of(2023, 9, 20)),
				new Transaction(5L, 101L, 50.0, LocalDate.of(2023, 8, 25)),

				// Customer 2 transactions across three months
				new Transaction(6L, 102L, 90.0, LocalDate.of(2023, 7, 12)),
				new Transaction(7L, 102L, 110.0, LocalDate.of(2023, 8, 18)),
				new Transaction(8L, 102L, 130.0, LocalDate.of(2023, 8, 22)),
				new Transaction(9L, 102L, 70.0, LocalDate.of(2023, 9, 10)),
				new Transaction(10L, 102L, 85.0, LocalDate.of(2023, 9, 25)),

				// Customer 3 transactions across three months
				new Transaction(11L, 103L, 105.0, LocalDate.of(2023, 7, 14)),
				new Transaction(12L, 103L, 115.0, LocalDate.of(2023, 8, 12)),
				new Transaction(13L, 103L, 140.0, LocalDate.of(2023, 8, 28)),
				new Transaction(14L, 103L, 60.0, LocalDate.of(2023, 9, 15)),
				new Transaction(15L, 103L, 95.0, LocalDate.of(2023, 9, 27))
		);

		Map<Long, Map<String, Integer>> customerRewards = lastThreeMonthsTransactions.stream()
				.collect(Collectors.groupingBy(Transaction::getCustomerId,
						Collectors.groupingBy(transaction -> transaction.getDate().getMonth().toString(),
								Collectors.summingInt(transaction -> calculatePoints(transaction.getAmount())))));

		List<RewardsResponse> rewardsResponses = new ArrayList<>();

		for (Map.Entry<Long, Map<String, Integer>> customerEntry : customerRewards.entrySet()) {
			Long customerId = customerEntry.getKey();
			Map<String, Integer> monthlyPoints = customerEntry.getValue();

			int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

			List<String> sortedMonths = monthlyPoints.keySet().stream().sorted((month1, month2) -> {
				Month m1 = Month.valueOf(month1.toUpperCase()); 
				Month m2 = Month.valueOf(month2.toUpperCase());
				return m2.compareTo(m1); 
			}).collect(Collectors.toList());

			ArrayList<RewardInMonth> rewardInMonths = new ArrayList<>();
			for (String month : sortedMonths) {
				rewardInMonths.add(new RewardInMonth(month, monthlyPoints.get(month)));
			}

			rewardsResponses.add(new RewardsResponse(rewardInMonths, totalPoints, customerId));
		}

		logger.info("Successfully calculated rewards for all customers. Total records: {}", rewardsResponses.size());
		return rewardsResponses;
	}

	/**
	 * Calculates the reward points based on the transaction amount.
	 *
	 * @param amount the transaction amount
	 * @return the calculated reward points
	 */
	private int calculatePoints(double amount) {
		if (amount > 100) {
			logger.debug("Transaction amount over $100: {}. Points: {}", amount, (int) ((amount - 100) * 2) + 50);
			return (int) ((amount - 100) * 2) + 50;
		} else if (amount > 50) {
			logger.debug("Transaction amount between $50 and $100: {}. Points: {}", amount, (int) (amount - 50));
			return (int) (amount - 50);
		}

		logger.debug("Transaction amount below $50: {}. No points earned.", amount);
		return 0;
	}
}
