# Homework Bank
This is intended to show simple working of a bank in terms of transaction. The following usecases are entertained:
* Predefined users for sake of simplicity no api for creating a customer is defined. It is assumed that username are unique so no validation is done if we have more than the same usernames.
* Validation of Iban, for simplicity only Iban in Germany are allowed.
* Validation for Transaction's time, only future transactions are entertained, but they are not scheduled for sake of simplicity.
* For simplicity, only EURO currency is supported.
## Technologies
Respecting the requirements, the following technologies are used:

Technology/Framework|Reason
------------- | -------------
[Jersey2](https://projects.eclipse.org/projects/ee4j.jersey/ ) | The simplest Java Restful implementation by Oracle and Eclipse. 
[HK2](https://javaee.github.io/hk2/) | Oracle's implementation of JSR-330 for DI and IoC.
[Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)| Java 8 for backend language.
[Swagger3.0](https://swagger.io/docs/specification/basic-structure/)  | For annotating the  api(s).
[Jetty](https://www.eclipse.org/jetty/) | The server to host and test the service.
[Cucumber](https://cucumber.io/)| For Acceptance Test Driven Development.
[Jooq](https://www.jooq.org/) | For sql first approach, to manage easy fluent query writing.
[Logstash](https://github.com/logstash/logstash-logback-encoder)| Log stash for structured logging and appending the old logs.
[H2 Database](https://www.h2database.com/html/main.html)| H2 engine for in memory database.
[Maven3](https://maven.apache.org/download.cgi)| For project management, build, packaging and dependency management.

## Scenario 1
Wolfgang Goethe owns Ali some money which he borrowed from him while compiling the Young Werther. After becoming famous writer, now he wants to payback Ali. Both are customer of 'Homework Bank' and are living in Germany.
Ali and Goethe have accounts in Euro. Goethe wants to transfer 1050 Euros to Ali's account.

## Scenario 2
Revolut wants to transfer 100 Euros to someone who does not have account in HomeWork Bank.

To showcase the working of the service, Acceptance Tests are presented to verify the above and more scenarios.

## Service
To accomplish the given scenario, I have the following service:
* transaction-service:
The transaction-service is responsible to handle a transaction. It is also responsible to handle the actual customer balance.


## Installation and Running Tests
``mvn clean install -U``

## Run
``mvn jetty:run``. The port for running the service is `8989` and for verifying the tests, the service is ran on port `9090`

## Api Definition
The open api specs can be viewed at `http://localhost:8989/openapi.json`

## Api examples
For simplicty some [Postman](https://www.getpostman.com/) examples are given in the source code at `postman-api-examples`. Please import the json file to Postman.

To play around the predefined customers are:

username | pin | iban
------- | ----------|----------
|aliilyas|ali786| DE75512108001245126199
|goethe|faust123|DE12500105170648489890
|revolut| homework |DE27100777770209299700
|poorguy|lessmoney|DE02273209963801668468
|johndoe|nowhereman| DE43560097633100252025

Some none Homework Bank ibans are :
DE34957931710796967291
DE18815774588533805761
DE14601765707550691710