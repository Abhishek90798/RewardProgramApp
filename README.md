# Retail Rewards App

## Overview
This project provides a rewards program for retail customers, enabling the calculation of reward points based on their transactions. Built with Spring Boot,
the application follows RESTful design principles and offers an API that allows clients to retrieve reward information for individual customers and all customers.

## Reward Calculation Rules
For each transaction made by customers over the last three months, the following reward points will be calculated based on the amount spent:
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
```
Navigate to the project directory:

```bash
cd RewardProgramApp
```
Use Maven to install the required dependencies:

```bash
mvn clean install
```

Configure the database:
```
spring.datasource.url=jdbc:mysql://localhost:3306/yourdb
spring.datasource.username=?
spring.datasource.password=?
spring.jpa.hibernate.ddl-auto=update
```

Run the application:
```
mvn spring-boot:run
```



### API Endpoints

1. **Get Rewards Points by CustomerId**  
   **GET** - `http://localhost:8081/api/rewards/{customerId}`
   
   **ResponseLikeThis**
   ```
   {
    "customerId": 101,
    "monthlyPoints": [
        {
            "month": "October",
            "amount": 425
        },
        {
            "month": "September",
            "amount": 110
        },
        {
            "month": "August",
            "amount": 245
        }
    ],
    "totalPoints": 780
   }
```

3. **Get Rewards Points of All Customers**  
   **GET** - `http://localhost:8081/api/rewards/all`

   **Response**
   ```json
   [
    {
        "customerId": 101,
        "monthlyPoints": [
            {
                "month": "SEPTEMBER",
                "amount": 400
            },
            {
                "month": "AUGUST",
                "amount": 25
            },
            {
                "month": "JULY",
                "amount": 90
            }
        ],
        "totalPoints": 515
    },
    {
        "customerId": 102,
        "monthlyPoints": [
            {
                "month": "SEPTEMBER",
                "amount": 55
            },
            {
                "month": "AUGUST",
                "amount": 180
            },
            {
                "month": "JULY",
                "amount": 40
            }
        ],
        "totalPoints": 275
    },
    {
        "customerId": 103,
        "monthlyPoints": [
            {
                "month": "SEPTEMBER",
                "amount": 55
            },
            {
                "month": "AUGUST",
                "amount": 210
            },
            {
                "month": "JULY",
                "amount": 60
            }
        ],
        "totalPoints": 325
    }
  ]
```


