# bartap-backend
This is the backend for the Bartap project. 
This is a new version of the original bartap backend, which was a single application.  
The goal of this project is to create a microservice based version of the bartap application,
while introducing new elements like monitoring.

## Services
* API Gateway
    Gateway to the many services running this project
* (Coming) Security/user
* (Coming) Core
* (Coming) Customer
* (Coming) Product
* (Coming) Stock
* (Coming later) Monitoring

## Messaging
The microservices communicate with each other primarily event-based, with a messaging queue.
In this project Apache Kafka will be used.  
<img src="https://i0.wp.com/jd-bots.com/wp-content/uploads/2021/08/kafka-logo.jpg?fit=2400%2C1342&ssl=1" alt="drawing" width="45%"/>  
For queue insight on local builds, [Offset Explorer 2.2](https://kafkatool.com/download.html) can be used.

## Architecture
These microservices' architecture will be a Ports & Adapters or Hexagonal architecture.

## CI

## CD / Deployment

## Versioning

##Testing

## Security

## Docker
The project contains a docker compose setup to easily spin up a development environment for the global used containers (global messaging queue).  
Images:
* bitnami/ Zookeeper + Kafka:latest
