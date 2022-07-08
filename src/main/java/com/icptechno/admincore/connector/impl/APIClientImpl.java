package com.icptechno.admincore.connector.impl;

import com.icptechno.admincore.connector.APIClient;
import com.icptechno.admincore.exception.ClientConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class APIClientImpl implements APIClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> T postForObject(String uri, Object request, Class<T> responseType) {
        try {
            log.info("Request details {}", request);
            return restTemplate.postForObject(uri, request, responseType);
        } catch (Exception ex) {
            log.error("Request Error details {}", ex.getMessage());
            throw new ClientConnectionException();
        }
    }

    @Override
    public <T> T getForObject(String uri, String value, Class<T> responseType) {
        try {
            log.info("Request details {}", value);
            return restTemplate.getForObject(uri, responseType, value);
        } catch (Exception ex) {
            log.error("Request Error details {}", ex.getMessage());
            throw new ClientConnectionException();
        }
    }
}
