package com.example.shop.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.secret-key}")
    private String secretKey;

    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public PayPalHttpClient payPalHttpClient() {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, secretKey);
        if ("live".equalsIgnoreCase(mode)) {
            environment = new PayPalEnvironment.Live(clientId, secretKey);
        }
        return new PayPalHttpClient(environment);
    }
}