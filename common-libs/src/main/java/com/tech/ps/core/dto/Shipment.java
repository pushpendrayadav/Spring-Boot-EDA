package com.tech.ps.core.dto;

import java.util.UUID;

public record Shipment(
 UUID orderId,
 UUID paymentId) {

}
