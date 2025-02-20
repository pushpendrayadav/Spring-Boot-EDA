package com.tech.ps.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.tech.ps.core.dto.Shipment;
import com.tech.ps.core.dto.commands.ShipmentProcessCommand;
import com.tech.ps.core.dto.events.ShippmentCreatedEvent;
import com.tech.ps.entity.ShipmentEntity;
import com.tech.ps.service.ShipmentService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@KafkaListener(topics="${shipments.commands.topic.name}")
public class ShipmentCommandsHandler {

	@Autowired
    private  ShipmentService shipmentService;
    @Autowired
    private  KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${shipments.created.events.topic.name}")
    private  String shipmentCreatedEventsTopicName;


    @KafkaHandler
	public void handleCommand(@Payload ShipmentProcessCommand command) {
    	log.info("  ********    Handler for shipment commands incoked "+command.toString());
		Shipment shipment = new Shipment(command.orderId(), command.paymentId());
		ShipmentEntity shipmentDB = shipmentService.processShipment(shipment);
		log.info("  ********    shipment created "+shipmentDB.getId());
		ShippmentCreatedEvent event = new ShippmentCreatedEvent(shipmentDB.getId(), shipmentDB.getOrderId(),
				shipmentDB.getPaymentId());
		log.info("  ********    emitting shipment created event "+event.toString());
		kafkaTemplate.send(shipmentCreatedEventsTopicName, event);
	}
}
