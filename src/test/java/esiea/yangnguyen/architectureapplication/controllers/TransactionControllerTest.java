package esiea.yangnguyen.architectureapplication.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionStatusUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "springdoc.api-docs.enabled=false",  // Disable Swagger en test
        "springdoc.swagger-ui.enabled=false"
})
class TransactionControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setup() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void shouldCreateTransaction() {
        TransactionCreateDTO dto = new TransactionCreateDTO(1, 2, List.of(), List.of());

        TransactionDTO created = restClient.post()
                .uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(TransactionDTO.class);

        assertThat(created).isNotNull();
        assertThat(created.getRequesterId()).isEqualTo(1);
    }

    @Test
    void shouldGetTransactionById() {
        long transactionId = 1;

        shouldCreateTransaction();
        final TransactionDTO fetched = restClient.get()
                .uri("/transactions/" + transactionId)
                .retrieve()
                .body(TransactionDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(transactionId);
        assertThat(fetched.getRequesterId()).isEqualTo(1);
        assertThat(fetched.getRecipientId()).isEqualTo(2);
    }

    @Test
    void shouldDeleteTransactionById() {
        long transactionIdToDelete = 1;

        restClient.delete()
                .uri("/transactions/" + transactionIdToDelete)
                .retrieve()
                .toBodilessEntity();

        Transaction fetched = restClient.get()
                .uri("/transactions/" + transactionIdToDelete)
                .retrieve()
                .body(Transaction.class);

        assertThat(fetched).isNull();
    }

    @Test
    void shouldUpdateTransactionStatus() {
        int transactionId = 1;
        shouldCreateTransaction();
        TransactionStatusUpdateDTO dto = new TransactionStatusUpdateDTO(transactionId, TransactionStatus.ACCEPTED, 2);

        restClient.put()
                .uri("/transactions/status")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .toBodilessEntity();

        final TransactionDTO fetched = restClient.get()
                .uri("/transactions/" + transactionId)
                .retrieve()
                .body(TransactionDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getStatus()).isEqualTo(TransactionStatus.ACCEPTED);
    }
}
