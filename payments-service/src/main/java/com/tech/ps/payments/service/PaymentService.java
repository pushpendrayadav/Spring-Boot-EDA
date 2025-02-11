package com.tech.ps.payments.service;

import java.util.List;

import com.tech.ps.core.dto.Payment;

public interface PaymentService {
    List<Payment> findAll();

    Payment process(Payment payment);
}
