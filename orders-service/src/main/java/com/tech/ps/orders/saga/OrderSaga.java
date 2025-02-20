package com.tech.ps.orders.saga;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.tech.ps.core.dto.commands.ApproveOrderCommand;
import com.tech.ps.core.dto.commands.ProcessPaymentCommand;
import com.tech.ps.core.dto.commands.ReserveProductCommand;
import com.tech.ps.core.dto.commands.ShipmentProcessCommand;
import com.tech.ps.core.dto.events.OrderApprovedEvent;
import com.tech.ps.core.dto.events.OrderCreatedEvent;
import com.tech.ps.core.dto.events.PaymentProcessedEvent;
import com.tech.ps.core.dto.events.ProductReservedEvent;
import com.tech.ps.core.dto.events.ShippmentCreatedEvent;
import com.tech.ps.core.types.OrderStatus;
import com.tech.ps.orders.service.OrderHistoryService;

@Component
@KafkaListener(topics={
        "${orders.events.topic.name}",
        "${products.events.topic.name}",
        "${payments.events.topic.name}",
        "${shipments.events.topic.name}"
})
public class OrderSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String productsCommandsTopicName;
    private final OrderHistoryService orderHistoryService;
    private final String paymentsCommandsTopicName;
    private final String ordersCommandsTopicName;
    private final String shipmentCommandsTopicName;

    public OrderSaga(KafkaTemplate<String, Object> kafkaTemplate,
                     @Value("${products.commands.topic.name}") String productsCommandsTopicName,
                     OrderHistoryService orderHistoryService,
                     @Value("${payments.commands.topic.name}") String paymentsCommandsTopicName,
                     @Value("${orders.commands.topic.name}") String ordersCommandsTopicName,
                     @Value("${shipments.commands.topic.name}") String shipmentCommandsTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.productsCommandsTopicName = productsCommandsTopicName;
        this.orderHistoryService = orderHistoryService;
        this.paymentsCommandsTopicName = paymentsCommandsTopicName;
        this.ordersCommandsTopicName = ordersCommandsTopicName;
        this.shipmentCommandsTopicName=shipmentCommandsTopicName;
    }

    @KafkaHandler
    public void handleEvent(@Payload OrderCreatedEvent event) {

        ReserveProductCommand command = new ReserveProductCommand(
                event.getProductId(),
                event.getProductQuantity(),
                event.getOrderId()
        );

        kafkaTemplate.send(productsCommandsTopicName,command);
        orderHistoryService.add(event.getOrderId(), OrderStatus.CREATED);
    }

    @KafkaHandler
    public void handleEvent(@Payload ProductReservedEvent event) {

        ProcessPaymentCommand processPaymentCommand = new ProcessPaymentCommand(event.getOrderId(),
                event.getProductId(),event.getProductPrice(), event.getProductQuantity());
        kafkaTemplate.send(paymentsCommandsTopicName,processPaymentCommand);
    }
    
   // PaymentProcessedEvent
    
    @KafkaHandler
    public void handleEvent(@Payload PaymentProcessedEvent event) {

        ShipmentProcessCommand shipmentProcessCommand = new ShipmentProcessCommand(event.getOrderId(),
                event.getPaymentId());
        kafkaTemplate.send(shipmentCommandsTopicName,shipmentProcessCommand);
    }

    @KafkaHandler
    public void handleEvent(@Payload ShippmentCreatedEvent event) {
        ApproveOrderCommand approveOrderCommand = new ApproveOrderCommand(event.orderId());
        kafkaTemplate.send(ordersCommandsTopicName,approveOrderCommand);
    }

    @KafkaHandler
    public void handleEvent(@Payload OrderApprovedEvent event) {
        orderHistoryService.add(event.getOrderId(), OrderStatus.APPROVED);
    }
}
