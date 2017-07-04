# Microservices for shopkeepers and wholesalers interact

The Shopkeeper and Wholesaler microservices are two Spring Boot applications developed to allow shopkeepers and wholesalers do interactions, like buy and sell products. The codes in this repository are just a proof of concept and they are a part of the final pratical task of the discipline of **Backend Architecture and Microservices**, in the Distributed Software Architecture postgraduate.

#### Authors
- [Fernanda Montanari](https://github.com/fermontanari)
- [Renato Martins](https://github.com/renattomartins)

### To test the application

You can test this application by follow the postman collection inside the folder `postman`. The sequence of saved requests there represents all the business flow between the shopkeeper and the wholesaler, according to the statement of the discipline final work.

### Instructions to run the applications with Docker
1. Clone this repository
2. Go to the root project and type `./mvnw package docker:build` to build the both microservices and the Docker images. At this moment, it works just with Maven.
3. To run the Shopkeeper microservice, type: `docker run -p 8090:8090 -t asd/asd-abe-shopkeeper`
4. So, to run the Wholesaler microservice, type: `docker run -p 13080:13080 -t asd/asd-abe-wholesaler`
5. You can now visit http://localhost:8090/shopkeeper/v1/ and http://localhost:13080/wholesaler/v1/ to see the APIs initial resources

### Instructions to run the applications without Docker (i.e. in the host OS)
1. Clone this repository
2. Go to the root project and type `./mvnw clean package` (if you prefer maven) or `./gradlew build` (if you prefer gradle). You will build the both microservices.
3. To run the Shopkeeper microservice, type:
    1. `java -jar asd-abe-shopkeeper/target/asd-abe-shopkeeper-0.1.0.jar` (if you prefer maven)
    2. `java -jar asd-abe-shopkeeper/build/libs/asd-abe-shopkeeper-0.1.0.jar` (if you prefer gradle)
    3. You can now visit http://localhost:8090/shopkeeper/v1/ to see the API initial resources
4. To run the Wholesaler microservice, type:
    1. `java -jar asd-abe-wholesaler/target/asd-abe-wholesaler-0.1.0.jar` (if you prefer maven)
    2. `java -jar asd-abe-wholesaler/build/libs/asd-abe-wholesaler-0.1.0.jar` (if you prefer gradle)
    3. You can now visit http://localhost:13080/wholesaler/v1/ to see the API initial resources

## Shopkeeper microservice

Shopkeeper is a microservice for shopkeepers to request products and interact with wholesalers on proposals.

Exposed API for Shopkeeper:

* **GET** /orchestration/order/{id} - Gets the order {id}.
* **GET** /orchestration/orders - Gets all orders.
* **POST** /orchestration/order - Creates a new order.
* **POST** /orchestration/proposal/{id}/accept - Accepts the proposal {id}.
* **POST** /orchestration/proposal/{id}/reject - Rejects the proposal {id}.

Exposed API for other microservices: All other APIs under / are exposed for other microservices. There will be a authorization framework to only allow other microservices to access them.

**Complete documentation:** The complete documentation can be found at: http://localhost:8090/shopkeeper/v1/swagger-ui.html

## Wholesaler microservice
Wholesaler is a microservice for wholesaler to receive products requests and interact with shopkeepers on proposals and order tracking.

Exposed API for Wholesaler:

* **GET** /orchestration/order/{id} - Gets the order {id}.
* **GET** /orchestration/orders - Gets all orders.
* **POST** /orchestration/proposal - Creates a new proposal.
* **POST** /orchestration/order/{id}/manufactoring - Changes the order status.
* **POST** /orchestration/order/{id}/dispatch - Changes the order status.
* **POST** /orchestration/order/{id}/close - Changes the order status.

Exposed API for other Microservices: All other APIs under / are exposed for other microservices. There will be a authorization framework to only allow other microservices to access them.

**Complete documentation:** The complete documentation can be found at: http://localhost:13080/wholesaler/v1/swagger-ui.html
