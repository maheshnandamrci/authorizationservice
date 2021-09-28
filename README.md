# Microservices with Spring Boot Demo Project

In this project I'm demonstrating you the most interesting features of [Spring Boot Project](https://spring.io/projects/spring-cloud) for building microservice-based architecture.

### Usage

I have used Maven and JDK8. The best way to run the sample applications is with IDEs like Eclipse or Spring Tool suite.  

## Architecture

Our sample microservices-based system consists of the following modules:
- **authorization-service** - a module containing only one operation that allows to perform CRUD operation on in-memory repository of users.
- **userprofile-service** - a module containing three of our microservices that allows to perform CRUD operation on in-memory repository of users. It communicates with authorization-service. 

Note: UserProfile operations will be invoked from Authorization service after the user is authorized only. Authorization service will be the Gateway for user profile operations.

## Modules
Here are modules which i have used in this project from Spring Boot.

### spring-boot
The main library providing features that support the other parts of Spring Boot. These include:

* The `SpringApplication` class, providing static convenience methods that can be used to write a stand-alone Spring Application.
  Its sole job is to create and refresh an appropriate Spring `ApplicationContext`.
* Embedded web applications with a choice of container (Tomcat, Jetty, or Undertow). With externalized configuration support.


### spring-boot-autoconfigure
Spring Boot can configure large parts of typical applications based on the content of their classpath.
A single `@EnableAutoConfiguration` annotation triggers auto-configuration of the Spring context.

Auto-configuration attempts to deduce which beans a user might need. For example, if `HSQLDB` is on the classpath, and the user has not configured any database connections, then they probably want an in-memory database to be defined.
Auto-configuration will always back away as the user starts to define their own beans.


### spring-boot-starters
Starters are a set of convenient dependency descriptors that you can include in your application.
You get a one-stop-shop for all the Spring and related technology you need without having to hunt through sample code and copy paste loads of dependency descriptors.
For example, if you want to get started using Spring and JPA for database access, include the `spring-boot-starter-data-jpa` dependency in your project, and you are good to go.

### springfox-swagger
I used Springfox to include Swagger into our Spring projects. This allows us to use the Swagger UI tester to obtain some live documentation and testing for our REST APIs.  This will create a valid OpenAPI specification document and then render it using Swagger UI.

### spring-boot-test
This module contains core items and annotations that can be helpful when testing your application.

### JPA and persisting H2 database
* spring-boot-starter-data-jpa: JPA witth Hibernate for DB communication
* com.h2database:h2: H2 DB connector

### set up zookeeper and kafka for async communication

* download zookeeper and kafka tar files
* unpack and set in the environemnt variable like below;
   ZOOKEEPER_HOME as C:\Users\Mahesh\Downloads\zookeeper-3.6.3 and update the PATH also
* start zookeeper server from the command prompt
**zkserver**

* start kafka broker from the kafka folder
.\bin\windows\kafka-server-start.bat .\config\server.properties 

* create a topic “maheshtesttopic” with below command
 **kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic maheshtesttopic**

* consume from the topic using the console producer
**kafka-console-producer.bat --broker-list localhost:9092 --topic maheshtesttopic**

* produce something into the topic (write something and hit enter)
**kafka-console-consumer.bat --zookeeper localhost:2181 --topic maheshtesttopic**


### Run Local
```bash
$ mvn clean install
$ mvn spring-boot:run
```

### Running UserProfile service run details
* application will run on port **9080** (We can configure/change the port by changing `server.port` in __application.properties__ file)
* swagger ui URL - http://localhost:9080/swagger-ui.html
* h2 console UI - http://localhost:9080/h2/login.jsp - (This schema will have PROFILE_DETAILS table to store the User details like address and phonenumber) 
* mvn clean install will give us **userprofileservice.jar** file in my project -> target folder

### Running Authorization Service run details
* application will run on port **9090** (We can configure/change the port by changing `server.port` in __application.properties__ file)
* swagger ui URL - http://localhost:9090/swagger-ui.html
* h2 console UI - http://localhost:9090/h2/login.jsp  - (This schema will have AUTHORIZATION_DETAILS table to store the username and password details which will be used for user authentication.
* mvn clean install will give us **authorizationservice.jar** file in my project -> target folder

### steps to test the operations

* First build project 1(UserProfile service) and 2 (Authorization Service). Use Authorization Service end point url to test all the API's.
* insert one user data record in the Resgistration DB for authorization purpose (I was told to do a manual entry but I had created a simple API for inserting record)
* Check Login by invoking Auth service Login POST API - Using http://localhost:9090/assignement/login
* UserProfile will be created by invoking Auth service Login API with CREATE operation - http://localhost:9090/assignement/login - It internally calls UserProfile createuser POST API using rest template
* UserProfile will be updated by invoking Auth service Login API with UPDATE operation - http://localhost:9090/assignement/login - It internally calls UserProfile updateuser PUT API
* UserProfile will be deleted by invoking Auth service Login API with DELETE operation - http://localhost:9090/assignement/login - It internally calls UserProfile deleteuser DELETE API

### API details

#### Register a new user

```
POST http://localhost:9090/assignement/registerUser

Request Body:
{
	"username": "Mahathi", "password": "Nandam"
}

```

#### Login
```
POST http://localhost:9090/assignement/login

Request Body:
{
	"username": "Mahathi", "password": "Nandam"
}

```

#### Create a profile
```
POST http://localhost:9090/assignement/login

Request Body:
{
	"username": "Mahathi", 	"password": "Nandam", "operation":"Create",	"address":"India",	"phoneNumber":"9884494730"
}

Internally invokes POST http://localhost:9080/assignement/profile
```
#### Update a profile
```
POST http://localhost:9090/assignement/login

Request Body:
{
	"username": "Mahathi", "password": "Nandam", "operation":"Update", "address":"India", "phoneNumber":"9884494730"
}

Internally invokes PUT http://localhost:9080/assignement/profile
```
#### Delete a profile
```
POST http://localhost:9090/assignement/login

Request Body:
{
	"username": "Mahathi", 	"password": "Nandam", 	"operation":"Delete", 	"address":"India", 	"phoneNumber":"9884494730"
}

Internally invokes DELETE http://localhost:9080/assignement/profile
```

### Points to be noted
* Created a Sigle maven project with two microservices (Authorization and Userprofile) which will run on two different ports and packaged in to a single war.

 ![image](https://user-images.githubusercontent.com/54699915/135016488-b62d95c5-f21b-4e1a-a9b7-7d136bc7059c.png)

* The steps to run it is - Run project 1 on port 1 and run project 2 on port 2 and run the packaging project 3 on port 3. (3 different ports)
* But I used only project 1 and project 2 making project 1 (Auth Service) as a Gateway and finished the testing also.
* I used the @RequestMapping(value = "assignement") in both of my Restcontroller so my API's endpoints will have "assignement" in them.
* Postman collection cannot be attached here, I will be sending them in email as well as the Database screenshots of my unit testing.





