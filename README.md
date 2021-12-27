# Car Rental Project

A Web Application that acts as a car rental company. The user can view the available cars, then pay for the access to the selected package, and finally pick up his car. The main reason for me to create this application was to learn Junit and Mockito testing.

## Used Tools

- Java 17
- Spring Boot v2.5.5
- Maven v3.8.2
- MySQL Community Server v8.0.27
- Spring Data JPA
- Spring Web MVC
- Spring Security with JWT
- Lombok
- JUnit 5
- Mockito

## Features

### User perspective
- Login and Registration
- List of cars (all, available, packages)
- Adding a credit card and money transfers
- Ordering a car from the selected package
- Picking up the selected car

### Manager perspective
All User features and the following operations:
- Car operations (GET, POST)
- User operations (GET, POST)
- Save Role
- Save Car Package
- Get all orders

### Admin perspective
All Manager features and the following operations:
- Car operations (PUT, DELETE)
- User operations (PUT, DELETE)
- Add and Delete user role
- Add and Delete user credit card
- Delete Car Package

## Requirements

For building and running the application you need:

- [JDK 1.17](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3](https://maven.apache.org/download.cgi)

## How To Run

1. Install [MySQL](https://dev.mysql.com/downloads/installer/)
2. Execute command `git clone https://github.com/Tomasz3976/car-rental-project.git`
3. Execute command `cd car-rental-project`
4. Configure datasource in *main/resources/application.properties*
```
spring.datasource.url=jdbc:mysql://localhost:3306/carrentaldb
spring.datasource.username=username
spring.datasource.password=password
```
5. Configure datasource in *test/resources/application.properties*
```
spring.datasource.url=jdbc:mysql://localhost:3306/carrentaltestdb
spring.datasource.username=username
spring.datasource.password=password
```
6. Execute command `mvn clean install`
7. Execute command `mvn spring-boot:run`
8. The server is running on **localhost:8080**

The application can be used with Swagger or Postman

#### To login, enter the username and password for the account with selected role:
|   Role  	| Username 	| Password 	|
|:-------:	|:--------:	|:--------:	|
|   User  	|   user   	|   user   	|
| Manager 	|  manager 	|  manager 	|
|  Admin  	|   admin  	|   admin  	|


## Usage
Sample application requests:

### Registration of User -> "/registration/registerUser"

**JSON Request**

```json
{
  "firstName": "Adam",
  "lastName": "Johnson",
  "username": "john675",
  "password": "AJ765john",
  "email": "adam.johnson44@gmail.com",
  "phone": 809430118
}
```
### Adding Credit Card to logged in User -> "/registration/addCreditCard"

**JSON Request**

```json
{
  "cardNumber": 8456800984550002,
  "cvv": 572,
  "month": 11,
  "year": 2022
}
```

### Ordering a Car from the selected Package -> "/orders"

**Request Params**

```java
carPackage: "Luxury"
hours: 2
```

### Picking up the car from the selected Package -> "/delivery"

**Request Params**

```java
carId: 4
```
