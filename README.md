# Event-driven-Architecture using Spring Boot Kafka
This project demonstrates Event-Driven Architecture (EDA) using Spring Boot and Apache Kafka. It follows the SAGA Orchestration Design Pattern to manage distributed transactions across microservices.


# Overview

In a microservices-based system, managing transactions across multiple services is challenging. This project implements SAGA Orchestration using Kafka to ensure data consistency while enabling services to work independently.

# Services Description

~~~

Inventory-Service: Manages stock and updates availability.

Order-Service: Handles order creation, emits order_created_event & Manages the SAGA workflow and coordinates events.

Payment-Service: Processes payments and publishes status updates.

common-libs: it contains shared utilities, DTOs, and configurations used across multiple services.

credit-card-processor-service: Credit Card Processor Service to show some dummy processing of payment to handle fail/pass scenerios.

Kafka Broker: Acts as the event streaming/processing for communication.

~~~