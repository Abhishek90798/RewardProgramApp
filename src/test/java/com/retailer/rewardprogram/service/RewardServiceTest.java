package com.retailer.rewardprogram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.retailer.rewardprogram.dao.ITransactionsRepository;
import com.retailer.rewardprogram.dto.RewardInMonth;
import com.retailer.rewardprogram.dto.RewardsResponse;
import com.retailer.rewardprogram.exceptions.CustomerNotFoundException;
import com.retailer.rewardprogram.model.Transaction;
import com.retailer.rewardprogram.service.impl.RewardService;

/**
 * Unit tests for {@link RewardService}.
 */
class RewardServiceTest {

    @Mock
    private ITransactionsRepository repository;

    @InjectMocks
    private RewardService rewardService;

    /**
     * Initializes mock objects before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the reward calculation for a specific customer.
     */
    @Test
    void testSpecificCustomersWithRewards() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, 101L, 120.0, LocalDate.of(2023, 7, 10)),
                new Transaction(2L, 101L, 75.0, LocalDate.of(2023, 8, 15)),
                new Transaction(3L, 101L, 150.0, LocalDate.of(2023, 9, 5)),
                new Transaction(4L, 101L, 200.0, LocalDate.of(2023, 9, 20))
        );

        RewardsResponse expectedResponse = new RewardsResponse(
                new ArrayList<>(Arrays.asList(
                        new RewardInMonth("JULY", 90),
                        new RewardInMonth("AUGUST", 25),
                        new RewardInMonth("SEPTEMBER", 400)
                )),
                515, 101L
        );

        when(repository.findByCustomerId(101L)).thenReturn(transactions);

        RewardsResponse actualResponse = rewardService.calculateRewards(101L);

        assertEquals(expectedResponse.getTotalPoints(), actualResponse.getTotalPoints());
        assertEquals(expectedResponse.getCustomerId(), actualResponse.getCustomerId());
        assertEquals(expectedResponse.getMonthlyPoints().size(), actualResponse.getMonthlyPoints().size());
    }
    
    /**
     * Tests that an exception is thrown when the customer is not found.
     */
    @Test
    void testCustomerNotFound() {
        long customerId = 999L;
        when(repository.findByCustomerId(customerId)).thenReturn(new ArrayList<>());
        assertThrows(CustomerNotFoundException.class, () -> rewardService.calculateRewards(customerId));
    }

    /**
     * Tests reward calculation for all customers.
     */
    @Test
    void testGetAllCustomersWithRewards() {
        List<RewardsResponse> expectedResponses = Arrays.asList(
                new RewardsResponse(
                        new ArrayList<>(Arrays.asList(
                                new RewardInMonth("JULY", 90),
                                new RewardInMonth("AUGUST", 25),
                                new RewardInMonth("SEPTEMBER", 400)
                        )),
                        515, 101L
                ),
                new RewardsResponse(
                        new ArrayList<>(Arrays.asList(
                                new RewardInMonth("JULY", 40),
                                new RewardInMonth("AUGUST", 180),
                                new RewardInMonth("SEPTEMBER", 55)
                        )),
                        275, 102L
                ),
                new RewardsResponse(
                        new ArrayList<>(Arrays.asList(
                                new RewardInMonth("JULY", 60),
                                new RewardInMonth("AUGUST", 210),
                                new RewardInMonth("SEPTEMBER", 55)
                        )),
                        325, 103L
                )
        );

        List<RewardsResponse> actualResponses = rewardService.calculateRewardsForAll();

        assertEquals(expectedResponses.size(), actualResponses.size());

        verifyResponse(expectedResponses.get(0), actualResponses, 101L);
        verifyResponse(expectedResponses.get(1), actualResponses, 102L);
        verifyResponse(expectedResponses.get(2), actualResponses, 103L);
    }

    /**
     * Helper method to verify response for a specific customer.
     *
     * @param expected The expected response for the customer.
     * @param actualResponses The actual list of responses to be verified.
     * @param customerId The customer ID to match.
     */
    private void verifyResponse(RewardsResponse expected, List<RewardsResponse> actualResponses, long customerId) {
        Optional<RewardsResponse> actualResponse = actualResponses.stream()
                .filter(response -> response.getCustomerId() == customerId)
                .findFirst();

        if (actualResponse.isPresent()) {
            assertEquals(expected.getTotalPoints(), actualResponse.get().getTotalPoints());
            assertEquals(expected.getCustomerId(), actualResponse.get().getCustomerId());
            assertEquals(expected.getMonthlyPoints().size(), actualResponse.get().getMonthlyPoints().size());
        }
    }
}
