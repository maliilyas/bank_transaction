# Homework Bank
This is intended to show simple working of a bank in terms of transaction. The following usecases are entertained:
* Predefined users for sake of simplicity no api for creating a customer is defined. It is assumed that username are unique so no validation is done if we have more than same usernames.
* Validation for predefined customers.
* Validation of Iban, for simplicity only Iban in Germany are allowed.
* For simplicity, only EURO currency is supported.
## Technologies
Respecting the requirements, the following technologies are used:

Technology/Framework|Reason
------------- | -------------
[Jersey2](https://projects.eclipse.org/projects/ee4j.jersey/ ) | The simplest Java Restful implementation by Oracle and Eclipse. 
[HK2](https://javaee.github.io/hk2/) | Oracle's implementation of JSR-330 for DI and IoC.
[Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)| Java 8 for backend language.
[Swagger3.0](https://swagger.io/docs/specification/basic-structure/)  | For annotating the  api(s).
[Jetty](https://www.eclipse.org/jetty/) | For embedded server to run and test the services.
[Cucumber](https://cucumber.io/)| For Acceptance Test Driven Development.
[Jooq](https://www.jooq.org/) | For sql first approach, to manage easy fluent query writing.
[Logstash](https://github.com/logstash/logstash-logback-encoder)| Log stash for structured logging and appending the old logs.
[H2 Database](https://www.h2database.com/html/main.html)| H2 engine for in memory database.
[Maven3](https://maven.apache.org/download.cgi)| For project management, build, packaging and dependency management.

## Scenario
Wolfgang Goethe owns Ali some money which he borrowed from him while compiling the Young Werther. After becoming famous writer, now he wants to payback Ali. Both are customer of 'Homework Bank' and are living in Germany.
There is a small catch, Ali and Goethe have accounts in Euro. Goethe wants to transfer 1050 Euros to Ali's account.

## Service
To accomplish the given scenario, I have the following service:
* transaction-service:
The transaction-service is responsible to handle a transaction. It is also responsible to handle the actual customer balance.

## Packaging
```mvn clean package -U```

## Installation
``mvn clean install -U``

## Run
``mvn jetty:run``

## Api Definition
The open api specs can be viewed at `http://localhost:8989/openapi.json`

## Api examples
For simplicty some [Postman](https://www.getpostman.com/) examples are given in the source code at `postman-api-examples`. Please import the json file to Postman.
