# Event-Driven Architecture (EDA) with Saga Orchestration

# Order Creation Process

1. 1.0.  The Order Service emits an order_created_event when a new order is placed.
1. 1.1.  The order details are stored in the Order DB.

# Product Reservation Process

1.  2.0: The Saga Orchestration Service sends a reserve_product_command to the Inventory Service.
2.  2.1: The Inventory Service checks product availability and updates the Inventory DB accordingly.
3.  3.0:  The Inventory Service emits reserved_events when the product is successfully reserved.

# Payment Processing

1.  4.0: Upon receiving reserved_events, the Saga Orchestration Service sends a payment_process_command to the Process-Payment Service.
2.  5.0: The Process-Payment Service initiates an asynchronous credit card API call to an external payment provider.
3.  5.1: If successful, the transaction is stored in the Payment DB.
4.  5.2: The Process-Payment Service emits payment_processed_events.

# Shipment Processing

1.  6.0: The Saga Orchestration Service sends a prepare_shipment_command to the Shipment Service.
2.  6.1: The Shipment Service stores shipping details in the Shipment DB.
3.  6.2: It then emits a shipment_event to indicate successful shipment preparation.

# Order Completion

1.  7.0: The Saga Orchestration Service sends a complete_order_command to the Order Service, marking the order as completed.


# Key Takeaways:

1.  Event-driven orchestration ensures microservices are decoupled.
2.  Saga Orchestration manages distributed transactions across services.
3.  Asynchronous Kafka-based communication improves scalability.
4.  Ensures consistency across inventory, payment, and shipping.
