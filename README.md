# Microservices for shopkeepers and wholesalers interact

The Shopkeeper and Wholesaler microservices are two Spring Boot applications developed to allow shopkeepers and wholesalers do interactions, like buy and sell products. The codes in this repository are just a proof of concept and they are a part of the final pratical task of the discipline of **Backend Architecture and Microservices**, in the Distributed Software Architecture postgraduate.

## Shopkeeper microservice

Shopkeeper is a microservice for shopkeepers to request products and interact with wholesalers on proposals.

### Documentation
Swagger documentation: http://localhost:8090/shopkeeper/v1/swagger-ui.html/

### Exposed API for Shopkeeper
* **GET** /orchestration/order/{id}
* **GET** /orchestration/orders
* **POST** /orchestration/order
* **POST** /orchestration/proposal/{id}/accept
* **POST** /orchestration/proposal/{id}/reject

### Exposed API for other microservices
All other APIs under / are exposed for other microservices. There will be a authorization framework to only allow other microservices to access them.

## Wholesaler microservice
Wholesaler is a microservice for wholesaler to receive products requests and interact with shopkeepers on proposals and order tracking.

### Documentation
Swagger documentation: http://localhost:13080/wholesaler/v1/swagger-ui.html/

### Exposed API for Wholesaler
* **GET** /orchestration/order/{id}
* **GET** /orchestration/orders
* **POST** /orchestration/proposal
* **POST** /orchestration/order/{id}/manufactoring
* **POST** /orchestration/order/{id}/dispatch
* **POST** /orchestration/order/{id}/close

### Exposed API for other Microservices
All other APIs under / are exposed for other microservices. There will be a authorization framework to only allow other microservices to access them.
