package com.tech.ps.dto;

import java.util.UUID;

public record ShipmentResponse(UUID shipmentId,UUID orderId,
		 UUID paymentId) {

}
