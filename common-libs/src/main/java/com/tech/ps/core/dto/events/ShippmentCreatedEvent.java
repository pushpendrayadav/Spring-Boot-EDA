package com.tech.ps.core.dto.events;

import java.util.UUID;

public record ShippmentCreatedEvent(UUID shipmentID,UUID orderId, UUID paymentId) {

}
