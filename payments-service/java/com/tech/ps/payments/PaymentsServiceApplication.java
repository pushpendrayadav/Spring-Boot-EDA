package com.tech.ps.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class PaymentsServiceApplication{

    public static void main(String[] args) {
    	log.info(" ******   starting payment mS...");
        SpringApplication.run(PaymentsServiceApplication.class, args);
    }
}
