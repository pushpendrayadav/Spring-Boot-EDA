package com.tech.ps.core.dto.commands;

import java.util.UUID;

public record ShipmentProcessCommand(UUID orderId,  UUID paymentId) {

}
