package com.tech.ps.ccps.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tech.ps.core.dto.CreditCardProcessRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("ccp")
public class CreditCardProcessorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardProcessorController.class);

    @PostMapping("/process")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void processCreditCard(@RequestBody @Valid CreditCardProcessRequest request) {
        LOGGER.info("Processing request: {}", request);
    }
}
