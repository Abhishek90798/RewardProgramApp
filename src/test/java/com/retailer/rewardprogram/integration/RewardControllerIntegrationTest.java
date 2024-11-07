package com.retailer.rewardprogram.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.retailer.rewardprogram.controller.RewardController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RewardController} class.
 * This class verifies the behavior of reward-related endpoints,
 * including getting rewards for a specific customer, retrieving rewards for all customers, 
 * and handling not found scenarios.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RewardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Setup method that is executed before each test case.
     * It can be used to initialize any resources needed for the tests.
     */
    @BeforeEach
    public void setUp() {
        // Any setup needed before each test can be added here
    }

    /**
     * Test for retrieving rewards points for a specific customer.
     * Verifies that the API correctly retrieves rewards points for the given customer ID.
     *
     * @throws Exception if an error occurs during the request execution
     */
    @Test
    public void testGetRewards() throws Exception {
        long customerId = 101L;

        mockMvc.perform(get("/api/rewards/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPoints").exists())
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.monthlyPoints").isArray());
    }

    /**
     * Test for retrieving rewards points for a customer that does not exist.
     * Verifies that the API returns a NOT_FOUND status when no rewards are found for the given customer ID.
     *
     * @throws Exception if an error occurs during the request execution
     */
    @Test
    public void testGetRewardsNotFound() throws Exception {
        long customerId = 999L;

        mockMvc.perform(get("/api/rewards/{customerId}", customerId))
                .andExpect(status().isNotFound());
    }

    /**
     * Test for retrieving rewards for all customers.
     * Verifies that the API correctly returns a list of rewards for all customers.
     *
     * @throws Exception if an error occurs during the request execution
     */
    @Test
    public void testGetAllRewards() throws Exception {
        mockMvc.perform(get("/api/rewards/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].customerId").exists())
                .andExpect(jsonPath("$[0].monthlyPoints").isArray());
    }
}
