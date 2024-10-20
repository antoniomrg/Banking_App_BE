# Simple Spring Boot Banking App 🏦

## Overview

This is a simple banking application built with Spring Boot, designed to manage users and their bank accounts. The application allows for user registration, bank account management, and transaction history retrieval.

## Features

👤 - **User Management**: Add and retrieve user information.

💶 - **Bank Account Management**: Create bank accounts for users, check balances, and perform transactions (deposit and withdrawal).

📜 - **Transaction History**: Retrieve the last five transactions for a specific bank account.

🚑 - **Error Handling**: Global exception handling for common error scenarios.

## Technologies Used 💻

- Java
- Spring Boot
- Spring Data JPA
- Lombok
- RESTful APIs
- OpenAPI (Swagger) for API documentation
- MySQL
- Maven

## Architecture 🏛️

The application follows a **Layered Architecture** comprising the following layers:

1. **Controller Layer**: 
   - Handles HTTP requests and responses via REST controllers, delegating operations to the service layer.

2. **Service Layer**: 
   - Contains business logic, encapsulated in classes like `BankAccountService`, `BankTransactionService`, and `UserService`, which manage users, bank accounts, and transactions.

3. **Repository Layer (Data Access)**: 
   - Interacts with the database through repositories (`BankAccountRepository`, `BankTransactionRepository`, `UserRepository`), providing CRUD operations and custom queries.

4. **Model Layer**: 
   - Defines the data structure with entity classes such as `BankAccount`, `User`, and `BankTransaction`.

5. **Mapping Layer**: 
   - Uses DTOs (`BankAccountDto`, `UserDto`) and mappers (`BankAccountMapper`, `UserMapper`) to convert between entities and DTOs, ensuring a separation of internal data representation from the API.

## API Endpoints

### User Endpoints

- **Add a new user**
  - `POST /users`

- **Get all users**
  - `GET /users`

- **Get a specific user by ID**
  - `GET /users/{userId}`

### Bank Account Endpoints

- **Add a new bank account for a user**
  - `POST /accounts`

- **Get balance by account number**
  - `GET /accounts/balance`

### Bank Transaction Endpoints

- **Make a deposit**
  - `PUT /accounts/deposit`

- **Make a withdrawal**
  - `PUT /accounts/withdrawal`

- **Get last five transactions**
  - `GET /accounts/history`

