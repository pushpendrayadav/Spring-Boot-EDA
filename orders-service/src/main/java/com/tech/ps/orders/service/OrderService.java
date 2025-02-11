package com.tech.ps.orders.service;

import java.util.UUID;

import com.tech.ps.core.dto.Order;

public interface OrderService {
    Order placeOrder(Order order);
    void approveOrder(UUID orderId);
}
