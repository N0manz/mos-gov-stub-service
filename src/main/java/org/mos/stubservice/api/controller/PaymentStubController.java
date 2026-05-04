package org.mos.stubservice.api.controller;

import org.mos.stubservice.service.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.mos.stubservice.api.generated.PaymentApi;
import org.springframework.web.server.ServerWebExchange;
import org.mos.stubservice.api.generated.dto.PaymentDto;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class PaymentStubController implements PaymentApi {

    private static final Duration STUB_DELAY = Duration.ofMillis(200);

    private final PaymentService paymentService;

    public PaymentStubController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Mono<ResponseEntity<PaymentDto>> getPayment(ServerWebExchange exchange) {
        return paymentService.generateRandom()
                .delayElement(STUB_DELAY)
                .map(ResponseEntity::ok);
    }
}
