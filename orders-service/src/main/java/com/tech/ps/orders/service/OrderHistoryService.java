package com.tech.ps.orders.service;

import java.util.List;
import java.util.UUID;

import com.tech.ps.core.types.OrderStatus;
import com.tech.ps.orders.dto.OrderHistory;

public interface OrderHistoryService {
    void add(UUID orderId, OrderStatus orderStatus);

    List<OrderHistory> findByOrderId(UUID orderId);
}
