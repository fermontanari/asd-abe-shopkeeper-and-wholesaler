# Microservices for shopkeeper and wholesaler interact, buy and sell products
# asd-abe-shopkeeper-and-wholesaler

---

## Shopkeeper
**Description**: Microsservice for shopkeepers to request products and interact with wholesalers on proposals

> ### **Swagger**
* **Swagger documentation: ** | http://localhost:8090/shopkeeper/v1/swagger-ui.html#/

> ### **Exposed API for Shopkeeper**
* ** GET ** | /orchestration/order/{id}
* ** GET ** | /orchestration/orders
* ** POST ** | /orchestration/order
* ** POST ** | /orchestration/proposal/{id}/accept
* ** POST ** | /orchestration/proposal/{id}/reject

> ### **Exposed API for other Microservices**
* ** All other APIs under / are exposed for other microservices. There will be a authorization framework to only allow other microservices to access them **


-----------------


## Wholesaler
**Description**: Microsservice for wholer to receive productsr requests and interact with shopkeepers on proposals and order tracking.

> ### **Swagger**
* **Swagger documentation: ** | http://localhost:13080/wholesaler/v1/swagger-ui.html#/

> ### **Exposed API for Wholesaler**
* ** GET ** | /orchestration/order/{id}
* ** GET ** | /orchestration/orders
* ** POST ** | /orchestration/proposal
* ** POST ** | /orchestration/order/{id}/manufactoring
* ** POST ** | /orchestration/order/{id}/dispatch
* ** POST ** | /orchestration/order/{id}/close

> ### **Exposed API for other Microservices**
* ** All other APIs under / are exposed for other microservices. There will be a authorization framework to only allow other microservices to access them **

