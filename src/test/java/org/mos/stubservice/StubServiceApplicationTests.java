package org.mos.stubservice;

import org.junit.jupiter.api.Test;
import org.mos.stubservice.api.generated.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StubServiceApplicationTests {

    @LocalServerPort
    private int port;

    private WebClient webClient() {
        return WebClient.create("http://localhost:" + port);
    }

    @Test
    void getPayment_returnsValidPayment() {
        webClient().get()
                .uri("/api/v1/payment")
                .retrieve()
                .bodyToMono(PaymentDto.class)
                .as(StepVerifier::create)
                .assertNext(payment -> {
                    assertThat(payment.getId()).isNotNull();
                    assertThat(payment.getFromAccount()).matches("^ACC-\\d{6}$");
                    assertThat(payment.getToAccount()).matches("^ACC-\\d{6}$");
                    assertThat(payment.getAmount()).isPositive();
                    assertThat(payment.getStatus()).isNotNull();
                    assertThat(payment.getCurrency()).isNotNull();
                })
                .expectComplete()
                .verify(Duration.ofSeconds(2));
    }

    @Test
    void getPayment_respondsIn200msNotBlocking() {

        webClient().get()
                .uri("/api/v1/payment")
                .retrieve()
                .bodyToMono(PaymentDto.class)
                .block(Duration.ofSeconds(5));


        var requests = Flux.range(0, 20)
                .flatMap(i -> webClient().get()
                        .uri("/api/v1/payment")
                        .retrieve()
                        .bodyToMono(PaymentDto.class))
                .collectList();

        StepVerifier.create(requests)
                .assertNext(list -> assertThat(list).hasSize(20))
                .expectComplete()
                .verify(Duration.ofMillis(1500));
    }
}
