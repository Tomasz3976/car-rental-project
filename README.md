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

## Explore Rest APIs

The app defines following CRUD APIs

### For Admin:

| Method | Url | Description |
| ------ | --- | ----------- |
| PUT    | /admin/users/{id} | Edit user |
| DELETE | /admin/users/{id} | Delete user |
| PUT    | /admin/users/{username}/roles | Add role to user |
| DELETE | /admin/users/{username}/roles/{roleName} | Delete user role |
| PUT    | /admin/users/{username}/creditCards | Add credit card to user |
| DELETE | /admin/users/{username}/creditCards | Delete user credit card |
| PUT    | /admin/cars/{id} | Edit car |
| DELETE | /admin/cars/{id} | Delete car |
| DELETE | /admin/cars/packages/{id} | Delete car package |

### For Manager (admin also):

| Method | Url | Description |
| ------ | --- | ----------- |
| GET    | /admin/users/all | Get all users |
| POST   | /admin/users | Save new user |
| POST   | /admin/roles | Save new role |
| GET    | /admin/cars/{id} | Get car by id |
| POST   | /admin/cars | Save new car |
| POST   | /admin/cars/packages| Save new car package |
| GET    | /admin/orders| Get all orders |

### For User (admin and manager also):

| Method | Url | Description |
| ------ | --- | ----------- |
| GET    | /cars/packages | Get all car packages |
| GET    | /cars/all | Get all cars |
| GET    | /cars/available | Get available cars |
| POST   | /registration/registerUser | Register new user |
| POST   | /registration/addCreditCard | Add credit card to logged in user |
| PUT    | /registration/moneyTransfer | Money transfer for logged in user |
| POST   | /orders | Place an order for given car package |
| POST   | /delivery | Rent a car from given car package |

## Sample Application Requests

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
