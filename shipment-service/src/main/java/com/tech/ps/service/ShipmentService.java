package com.tech.ps.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.ps.core.dto.Shipment;
import com.tech.ps.dto.ShipmentResponse;
import com.tech.ps.entity.ShipmentEntity;
import com.tech.ps.repo.ShipmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShipmentService {
	@Autowired
	private ShipmentRepository shipmentRepository;

	public ShipmentEntity processShipment(Shipment shipment) {
		log.info(" *******  ShipmentService.processShipment() method : " + shipment.toString());
		ShipmentEntity shipmentDB = new ShipmentEntity();
		BeanUtils.copyProperties(shipment, shipmentDB);
		shipmentDB.setShippedAt(LocalDateTime.now());
		return shipmentRepository.save(shipmentDB);
	}

	public List<ShipmentResponse> findAll() {
		return shipmentRepository.findAll().stream()
				.map(shipment -> new ShipmentResponse(shipment.getId(), shipment.getOrderId(), shipment.getPaymentId()))
				.toList();
	}
}
