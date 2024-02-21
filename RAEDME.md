# Online Bookstore Application

**Welcome to the Online Bookstore Application!**
This platform was developed to simplify book management, category organization, and user order handling.
The goal of the project was to provide an intuitive interface and enable book management in various ways and order placement.

*The Online Bookstore Application offers a range of functionalities, including:*

1. **👤User Registration and Authentication**: Allows users to create accounts, log in securely, and maintain their profiles, enhancing personalized experiences.
2. **📦User Order Handling**: Facilitates the ordering process, from cart creation to order placement and status tracking. Users can conveniently manage their orders through their accounts.
3. **📘Book Management**: Allows administrators to add, edit, and remove books from the inventory. Users can browse books by category and view detailed information about each book.
4. **📚Category Organization**: Enables the categorization of books into various genres or classifications, aiding users in discovering books based on their preferences.
5. **🛒Shopping Cart Management**: Provides users with a personalized shopping cart, allowing the addition, removal, and modification of book items before checkout.


## Technologies Used
- **☕️ JDK 17**: Embracing the functionalities and enhancements introduced in Java 17.
- **🍃 Spring Boot**: Backend development framework.
- **🛡️ Spring Security**: Authentication and authorization.
- **💾 Spring Data JPA**: Data persistence and database operations.
- **🗺️ MapStruct**: Object mapping between DTOs and models.
- **📘 Swagger**: API documentation generation.
- **✅ Jakarta Validation**: Ensuring data integrity with validation annotations.
- **🔄Liquibase**: Database schema version control.
- **🐬 MySQL**: Relational database management system.
- **🌶️ Lombok️**: Reducing boilerplate code in Java classes.

## Functionality Overview

1. **Authentication Controller** 👤
   - **Endpoints**:
      - `POST /api/auth/register`: Register a new user (PUBLIC).
      - `POST /api/auth/login`: User login functionality (PUBLIC).
        
2. **Book Controller** 📘
    - **Endpoints**:
       - `POST /books`: Create a new book (ADMIN).
       - `GET /books/{id}`: Get book by ID (USER).
       - `GET /books`: Retrieve all books (USER).
       - `PUT /books/{id}`: Update a book by ID (ADMIN).
       - `DELETE /books/{id}`: Delete a book by ID (ADMIN).

3. **Category Controller** 📚
    - **Endpoints**:
       - `POST /categories`: Create a new category (ADMIN).
       - `GET /categories`: Retrieve all categories (USER).
       - `GET /categories/{id}`: Get category by ID (USER).
       - `PUT /categories/{id}`: Update a category by ID (ADMIN).
       - `DELETE /categories/{id}`: Delete a category by ID (ADMIN).

4. **Shopping Cart Controller** 🛒
   - **Endpoints**:
      - `POST /carts`: Add item to the shopping cart (USER).
      - `GET /carts`: Retrieve shopping cart by user ID (USER).
      - `PUT /carts/cart-items/{cartItemId}`: Update item quantity in the shopping cart (USER).
      - `DELETE /carts/cart-items/{cartItemId}`: Remove item from the shopping cart (USER).

5. **Order Controller** 📦
    - **Endpoints**:
       - `POST /orders`: Create a new order (USER).
       - `GET /orders`: Retrieve all orders (USER).
       - `GET /orders/{orderId}/items`: Get order details by ID (USER).
       - `PATCH /orders/orderId`: Update order status by ID (ADMIN).


## Setup and Usage

### Requirements
- JDK 17 or higher ☕️
- Maven 🚀

### Steps to Run
1. Clone the repository 📥
2. Configure `application.properties` with your database settings 🛠️
3. Run `mvn spring-boot:run` to start the application 💻

### API Endpoints
- Use Postman to explore and test available endpoints.
- Utilize the following endpoints:
    - User registration 📝
    - Book and category management 📚
    - Shopping cart operations 🛒
    - Order management 📦

## Additional Implemented Features

### Integration of Security 🔒
Successfully integrated Spring Security for user authentication and authorization, ensuring data protection.

### Advanced DTO Mapping 🗺️
Utilized MapStruct for precise and comprehensive object mapping, managing various scenarios and edge cases effectively.

### Swagger Integration 📋
Implemented Swagger for API documentation, simplifying understanding and interaction with endpoints through a user-friendly interface.

### Exception Handling 🚩
Implemented robust exception handling to provide clear and understandable error messages, ensuring smooth application flow even during unexpected scenarios.

### Pagination 📖
Implemented pagination for improved data handling, allowing the presentation of large data sets in manageable chunks for better user experience.

## Project Summary

The Bookstore Application is dedicated to providing a secure and efficient platform for book management, user engagement, and seamless order processing.
All users can register and log in. After users can see all books as well as info on only
a certain book, they can see all categories and all books present in one category.
Users can put a book into a shopping cart, and delete it from the shopping cart. Also, they can place an order, see their order history, and see items from a specific order.
Admins can modify DB: add a new book, update info on it, or delete it.

For inquiries or support, feel free to reach out at yuriynovakr@gmail.com