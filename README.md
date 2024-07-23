# invoice generator system

## Overview

POC for generating client invoices using the Spring boot and mail.

## API endpoints:

### Invoices
1. **GET api/invoice** - Get all invoices
2. **GET api/invoice/{id}** - Get invoice by id
3. **POST api/invoice** - Create a new invoice

    Payload:
    ```json
        {       
            "userId": 5,
            "invoiceDate": "2024-07-20T10:00:00Z",
            "dueDate": "2024-08-20T10:00:00Z",
            "totalAmount": 5678
        }
    ```
4. **PUT api/invoice/{id}** - Update an existing invoice

    Payload:
    ```json
        {       
            "userId": 5,
            "invoiceDate": "2024-07-20T10:00:00Z",
            "dueDate": "2024-08-20T10:00:00Z",
            "totalAmount": 1234
        }
    ```


### Products
1. **GET api/product** - Get all products
2. **GET api/product/{id}** - Get product by id
3. **POST api/product** - Create a new product

    
    ```json
        {
            "productName": "new prod",
            "unitPrice": 123
        }
    ```
4. **PUT api/product/{id}** - Update an existing product

    
    ```json
        {
            "productName": "new prod_update",
            "unitPrice": 456
        }
    ```
5. **GET api/invoice/download/{id}** - Download invoice by id
6. **POST api/invoice/email/** - Email invoice by id

    
    ```json
        {
            "invoiceId": 1,
        }
7. **POST api/invoice/bulk-email** - Email all invoices associated with a user

    
    ```json
        {
            "userId": 1,
        }
    ```
    
### User
1. **GET api/user** - Get all users
2. **GET api/user/{id}** - Get user by id
3. **POST api/user** - Create a new user

    
    ```json
        {
            "firstName": "John",
            "lastName": "doe",
            "email": "john2@example.com",
            "password": "password123"
        }
    ```
4. **PUT api/user/{id}** - Update an existing user
5. **POST api/auth/login** - Login user

    
    ```json
        {
            "email": "john2@example.com",
            "password": "password123"
        }
    ```


## Tech Stack used:
- Spring Boot, security, JPA, and mail.

## Setup
run the following command to start the application:

```bash
mvn spring-boot:run
java -jar target/demo_inv-0.0.1-SNAPSHOT.jar
```
