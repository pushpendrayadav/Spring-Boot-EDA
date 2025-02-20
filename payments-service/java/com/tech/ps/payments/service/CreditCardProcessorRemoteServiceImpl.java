package com.tech.ps.payments.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.tech.ps.core.CreditCardProcessorUnavailableException;
import com.tech.ps.core.dto.CreditCardProcessRequest;

@Service
public class CreditCardProcessorRemoteServiceImpl implements CreditCardProcessorRemoteService {
    private final RestTemplate restTemplate;
    private final String ccpRemoteServiceUrl;


    public CreditCardProcessorRemoteServiceImpl(
            RestTemplate restTemplate,
            @Value("${remote.ccp.url}") String ccpRemoteServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.ccpRemoteServiceUrl = ccpRemoteServiceUrl;
    }


    @Override
    public void process(BigInteger cardNumber, BigDecimal paymentAmount) {
        try {
            var request = new CreditCardProcessRequest(cardNumber, paymentAmount);
            restTemplate.postForObject(ccpRemoteServiceUrl + "/ccp/process", request, CreditCardProcessRequest.class);
        } catch (ResourceAccessException e) {
            throw new CreditCardProcessorUnavailableException(e);
        }
    }
}
