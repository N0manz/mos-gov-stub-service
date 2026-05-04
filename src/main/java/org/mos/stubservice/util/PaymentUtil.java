package org.mos.stubservice.util;

import org.mos.stubservice.api.generated.dto.PaymentDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PaymentUtil {

    private static final PaymentDto.CurrencyEnum[] CURRENCIES = PaymentDto.CurrencyEnum.values();
    private static final PaymentDto.StatusEnum[]   STATUSES   = PaymentDto.StatusEnum.values();
    private static final String[] DESCRIPTIONS = {
            "Grocery store payment","Online subscription","Restaurant bill", "Taxi service","Hotel booking","Flight ticket", "Medical services","Utility bill","Rent payment","Freelance work"
    };

    public String randomAccount(ThreadLocalRandom rnd) {
        return "ACC-" + String.format("%06d", rnd.nextInt(1_000_000));
    }

    public Double randomAmount(ThreadLocalRandom rnd) {
        return BigDecimal.valueOf(rnd.nextDouble(1.0, 10_000.0))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public <T> T pick(ThreadLocalRandom rnd, T[] array) {
        return array[rnd.nextInt(array.length)];
    }

    public PaymentDto buildRandom() {
        var rnd = ThreadLocalRandom.current();

        return new PaymentDto()
                .id(UUID.randomUUID())
                .fromAccount(randomAccount(rnd))
                .toAccount(randomAccount(rnd))
                .amount(randomAmount(rnd))
                .currency(pick(rnd, CURRENCIES))
                .status(pick(rnd, STATUSES))
                .description(pick(rnd, DESCRIPTIONS))
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC));
    }
}
