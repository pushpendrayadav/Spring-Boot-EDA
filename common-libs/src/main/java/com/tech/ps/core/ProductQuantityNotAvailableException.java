package com.tech.ps.core;

import java.util.UUID;

public class ProductQuantityNotAvailableException extends RuntimeException {
    private final UUID productId;
    private final UUID orderId;

    public ProductQuantityNotAvailableException(UUID productId, UUID orderId) {
        super("Product " + productId + " has insufficient quantity in the stock for order " + orderId);
        this.productId = productId;
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
