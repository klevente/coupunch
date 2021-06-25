# Coupunch

## Architecture

- Gateway: `spring-cloud-gateway`
- Authenticator
- Config server with discovery: `spring-cloud-consul`
- Instances

Each request arrives into the gateway, where it is authenticated and redirected to the required instance, where the request is fulfilled.

Authentication is achieved via shared sessions in redis.

Instance setup:
- postgres
- running Spring application
- This can be configured: db credentials, extra config options
  - these options can also come from the config server - where these can be edited on a UI (hopefully)
  - extra customization is a question as of now

## Domain-driven design

Find the connection points and ends to domain elements: https://martinfowler.com/bliki/BoundedContext.html and map the package structure using it!

API first!

## The Monolith

Start with a small monolith until the first version, prepare to split it there.

Use domain packages (like I have already done), extract common functionality elsewhere
- domains can become microservices
- common functions can become a library
- use DI: define interfaces publicly, implement them package-privately - less refactoring in the future

## Sync vs Async

Synchronous interfaces introduce a strong dependency between microservices! (but sometimes sync calls can be OK, though they can sign that two services need to be merged)

Asynchronous communication leads to eventual consistency. (You will get an email in a few moments vs. You have gotten an email).

Distributed transactions (2PC, Saga) indicate tight coupling - these services should be merged or redesigned (transactions should reside in a single service if possible, or only eventual consistency is achieved)

## Event Driven Architecture

Microservices model *domains* and send out *events* that happened - other services can then *react* to these events in their logic

| Pattern                      | Type  | Implementation |
| ---------------------------- | ----- | -------------- |
| Request/response             | Sync  | REST           |
| Commands req. blocking       | Sync  | REST           |
| Commands don't req. blocking | Async | Message        |
| Events                       | Async | Message        |

Reactive system: high level, based on loosely coupled components reacting to events.

Reactive programming: paradigm for creating async code that runs better in parallel.

### RabbitMQ

```bash
cd /opt/rabbitmq/sbin
rabbitmq-plugins enable rabbitmq_management
# restart container, credentials: guest/guest
```

Not sharing event classes is good as they can evolve independently, e.g. a subscriber might not need every field of the published event.

## Gateways

Spring Gateway allows **capturing path segments**. If we get an API call such as `http://localhost:8000/multiplication/attempts`, we could extract as a value and use it to map to the corresponding service's host and port. However, this approach is valid only when each microservice contrrains only one API domain. This is what is required for multiple stores!

Use Spring Cloud Actuator for getting real-time data of the gateway's status - good for managing company instances:

```properties
management.endpoint.gateway.enabled=true
```

Use Micrometer or other time-series dbs for application statistics:

```properties
spring.cloud.gateway.metrics.enabled=true
```

Refreshing the config is achieved via a POST to the Actuator's refresh input: `curl -XPOST http://gateway/actuator/refresh`

## Integration Testing with Consul

Disable Consul for tests: https://github.com/Book-Microservices-v2/chapter08c/blob/master/multiplication/src/test/resources/test.properties

## Centralized logging

Very important! - ELK  could be useful for this: https://www.elastic.co/what-is/elk-stack

There are custom appenders in Logback for the ELK stack.