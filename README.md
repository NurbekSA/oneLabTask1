# Online Crowdinvesting and Financing Search Service

## Project Overview

This project is an online platform designed to facilitate crowdinvesting and assist start-ups in attracting financing. It allows investors with limited funds to invest in projects with minimal commissions, considering both legal and financial aspects of crowdinvesting.


## Technologies Used

- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: H2
- **API Documentation**: Swagger (available at [Swagger UI](http://localhost:8080/swagger-ui/index.html#))


### API Authentication

To interact with secured endpoints, use Spring Security's authentication. By default, the system is set up with the following in-memory user credentials:
- **Username**: `OneLab`
- **Password**: `1234`

To access the API:
- Use tools like Postman or Swagger UI.
- Add the default credentials to authenticate and test the secured endpoints.

### Using Swagger

The platform includes Swagger for API documentation, available at [Swagger UI](http://localhost:8080/swagger-ui/index.html#). Use Swagger to:
- Browse available API endpoints.
- Execute requests and review responses.
- Easily test authentication and authorization.

### Additional Components

- **AuthControllerAspect**: This aspect logs all requests made to controllers, providing an audit trail for API interactions.

- **Global Exception Handling**: A `GlobalExceptionHandler` manages exceptions globally across the application:
  - **ResourceNotFoundException**: Returns a `404 Not Found` with a custom error message.
  - **Other Exceptions**: Returns a `500 Internal Server Error` for unexpected issues.

The `GlobalExceptionHandler` ensures consistent error responses across the application, making it easier for users to understand and debug issues.

---

This setup provides a comprehensive, secure, and user-friendly platform for crowdinvesting, with built-in logging and error handling to enhance transparency and maintainability.
