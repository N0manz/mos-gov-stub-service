package org.mos.stubservice.service.payment;

import org.mos.stubservice.util.PaymentUtil;
import org.springframework.stereotype.Service;
import org.mos.stubservice.api.generated.dto.PaymentDto;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    private final PaymentUtil paymentUtil;


    public PaymentService(PaymentUtil paymentUtil){
        this.paymentUtil = paymentUtil;
    }

    public Mono<PaymentDto> generateRandom() {
        return Mono.fromSupplier(paymentUtil::buildRandom);
    }
}
