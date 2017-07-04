# Microservices for shopkeepers and wholesalers interact

The Shopkeeper and Wholesaler microservices are two Spring Boot applications developed to allow shopkeepers and wholesalers do interactions, like buy and sell products. The codes in this repository are just a proof of concept and they are a part of the final pratical task of the discipline of **Backend Architecture and Microservices**, in the Distributed Software Architecture postgraduate.

### Instructions to run the applications
1. Clone this repository
2. Go to the root project and type `./mvnw clean package` (if you prefer maven) or `./gradlew build` (if you prefer gradle). You will build the both microservices.
3. To run the Wholesaler microservice, type:
    1. `java -jar asd-abe-wholesaler/target/asd-abe-wholesaler-0.1.0.jar` (if you prefer maven)
    2. `java -jar asd-abe-wholesaler/build/libs/asd-abe-wholesaler-0.1.0.jar` (if you prefer gradle)
    3. You can now visit http://localhost:13080/wholesaler/v1/ to see the API initial resources
4. To run the Shopkeeper microservice, type:
    1. `java -jar asd-abe-shopkeeper/target/asd-abe-shopkeeper-0.1.0.jar` (if you prefer maven)
    2. `java -jar asd-abe-shopkeeper/build/libs/asd-abe-shopkeeper-0.1.0.jar` (if you prefer gradle)
    3. You can now visit http://localhost:8090/shopkeeper/v1/ to see the API initial resources

## Shopkeeper microservice

Shopkeeper is a microservice for shopkeepers to request products and interact with wholesalers on proposals.

### Exposed API for Shopkeeper
* **GET** /orchestration/order/{id} - Gets the order {id}.
* **GET** /orchestration/orders - Gets all orders.
* **POST** /orchestration/order - Creates a new order.
* **POST** /orchestration/proposal/{id}/accept - Accepts the proposal {id}.
* **POST** /orchestration/proposal/{id}/reject - Rejects the proposal {id}.

### Exposed API for other microservices
All other APIs under / are exposed for other microservices. There will be a authorization framework to only allow other microservices to access them.

### Complete documentation
The complete documentation can be found at: http://localhost:8090/shopkeeper/v1/swagger-ui.html

## Wholesaler microservice
Wholesaler is a microservice for wholesaler to receive products requests and interact with shopkeepers on proposals and order tracking.

### Exposed API for Wholesaler
* **GET** /orchestration/order/{id} - Gets the order {id}.
* **GET** /orchestration/orders - Gets all orders.
* **POST** /orchestration/proposal - Creates a new proposal.
* **POST** /orchestration/order/{id}/manufactoring - Changes the order status.
* **POST** /orchestration/order/{id}/dispatch - Changes the order status.
* **POST** /orchestration/order/{id}/close - Changes the order status.

### Exposed API for other Microservices
All other APIs under / are exposed for other microservices. There will be a authorization framework to only allow other microservices to access them.

### Complete documentation
The complete documentation can be found at: http://localhost:13080/wholesaler/v1/swagger-ui.html
