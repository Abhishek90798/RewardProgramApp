# Retail Rewards App

## Overview
This project provides a rewards program for retail customers, enabling the calculation of reward points based on their transactions. Built with Spring Boot,
the application follows RESTful design principles and offers an API that allows clients to retrieve reward information for individual customers and all customers.

## Reward Calculation Rules
- Customers earn **2 points** for every dollar spent **over $100**.
- Customers earn **1 point** for every dollar spent **between $50 and $100**.

**Example**
A purchase of **$120** yields: [ 2*(120 - 100) + 1*(100 - 50) = **90** points ]

## Features
- RESTFul API to retrive customer reward points.
- Example Dataset Inculuded

## Technologies Used
- Java 8
- Spring Boot 2.5.10
- Maven
- Database -> MySql

## Getting Started

### Prerequisites
- Java 8
- Maven

## Installation

Clone the repository and navigate to the project directory:

```bash
git clone --branch master https://github.com/Abhishek90798/RewardProgramApp.git
cd RewardProgramApp
```

### API Endpoints

1. **Get Rewards Points by CustomerId**  
   **GET** - `http://localhost:8081/api/rewards/<customer_Id>`

2. **Get Rewards Points of All Customers**  
   **GET** - `http://localhost:8081/api/rewards/all`


