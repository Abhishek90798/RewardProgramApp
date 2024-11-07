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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewardprogram.dao.ITransactionsRepository;
import com.retailer.rewardprogram.dto.RewardInMonth;
import com.retailer.rewardprogram.dto.RewardsResponse;
import com.retailer.rewardprogram.exceptions.CustomerNotFoundException;
import com.retailer.rewardprogram.exceptions.CustomerNotFoundException;
import com.retailer.rewardprogram.model.Transaction;
import com.retailer.rewardprogram.service.IRewardService;

/**
 * Service class responsible for calculating reward points for customers
 * based on their transaction data.
 */
@Service
public class RewardService implements IRewardService{

    @Autowired
    private ITransactionsRepository repository;

    /**
     * Calculates the reward points for a specific customer based on their transactions
     * in the last three months.
     *
     * @param customerId the unique identifier of the customer
     * @return a RewardsResponse object containing the monthly points and total points
     */
    public RewardsResponse calculateRewards(Long customerId) {
        List<Transaction> transactions = repository.findByCustomerId(customerId);
        
        if (transactions.isEmpty()) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        // Group transactions by month and calculate points
        Map<String, Integer> monthlyPoints = transactions.stream().collect(
                Collectors.groupingBy(
                        transaction -> transaction.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                        Collectors.summingInt(transaction -> calculatePoints(transaction.getAmount()))
                )
        );

        // Sum the total points
        int sum = monthlyPoints.values().stream().mapToInt(ele -> ele).sum();
        ArrayList<RewardInMonth> list = new ArrayList<>();

        // Map monthly points to RewardInMonth and convert to RewardsResponse
        for (String key : monthlyPoints.keySet()) {
            int monthNo = Integer.parseInt(key.split("-")[1]);
            list.add(new RewardInMonth(Month.of(monthNo).getDisplayName(TextStyle.FULL, Locale.ENGLISH), monthlyPoints.get(key)));
        }

        return new RewardsResponse(new ArrayList<>(list), sum, customerId);
    }

    /**
     * Calculates the reward points for all customers based on their transactions
     * in the last three months.
     *
     * @return a list of RewardsResponse objects for all customers
     */
    public List<RewardsResponse> calculateRewardsForAll() {
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

        // Group transactions by customerId and month, and calculate points for each
        Map<Long, Map<String, Integer>> customerRewards = lastThreeMonthsTransactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCustomerId,  
                        Collectors.groupingBy(
                                transaction -> transaction.getDate().getMonth().toString(),  
                                Collectors.summingInt(transaction -> calculatePoints(transaction.getAmount()))  
                        )
                ));

        List<RewardsResponse> rewardsResponses = new ArrayList<>();

        for (Map.Entry<Long, Map<String, Integer>> customerEntry : customerRewards.entrySet()) {
            Long customerId = customerEntry.getKey();
            Map<String, Integer> monthlyPoints = customerEntry.getValue();

            // Calculate the total points for the customer
            int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

            ArrayList<RewardInMonth> rewardInMonths = new ArrayList<>();
            for (Map.Entry<String, Integer> monthEntry : monthlyPoints.entrySet()) {
                rewardInMonths.add(new RewardInMonth(monthEntry.getKey(), monthEntry.getValue()));
            }

            rewardsResponses.add(new RewardsResponse(rewardInMonths, totalPoints, customerId));
        }

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
            return (int) ((amount - 100) * 2) + 50;  
        } else if (amount > 50) {
            return (int) (amount - 50);  
        }
        return 0;  
    }
}
