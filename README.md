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
4. Configure datasource in *main/resources/application.yml*
```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/carrentaldb
    username: username
    password: password
```
5. Configure datasource in *test/resources/application.yml*
```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/carrentaltestdb
    username: username
    password: password
```
6. Execute command `mvn clean install`
7. Execute command `mvn spring-boot:run`
8. The server is running on **localhost:8080**

#### To login, enter the username and password for the account with selected role:
|   Role  	| Username 	| Password 	|
|:-------:	|:--------:	|:--------:	|
|   User  	|   user   	|   user   	|
| Manager 	|  manager 	|  manager 	|
|  Admin  	|   admin  	|   admin  	|

## Explore Rest APIs

To explore documentation, run the application and go to `http://localhost:8080/swagger-ui.html`
