package esiea.yangnguyen.architectureapplication.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import esiea.yangnguyen.architectureapplication.usecase.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "springdoc.api-docs.enabled=false",  // Disable Swagger en test
        "springdoc.swagger-ui.enabled=false"
})
class TransactionControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    private String token;

    @BeforeEach
    void setup() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        String uniqueEmail = "test" + System.currentTimeMillis() + "@mail.com";
        UserCreateDTO userCreateDTO = new UserCreateDTO("Test", "Test", uniqueEmail, "Test1234!");
        restClient.post()
                .uri("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(userCreateDTO)
                .retrieve()
                .body(UserDTO.class);

        UserAuthDTO userAuthDTO = new UserAuthDTO(uniqueEmail, "Test1234!");
        this.token = Objects.requireNonNull(restClient.post()
                        .uri("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userAuthDTO)
                        .retrieve()
                        .body(new ParameterizedTypeReference<Map<String, String>>() {
                        }))
                .get("token");
    }

    @Test
    void shouldCreateTransaction() {
        TransactionCreateDTO dto = new TransactionCreateDTO(1, 2, List.of(), List.of());

        TransactionDTO created = restClient.post()
                .uri("/transactions")
                .header("Authorization", "Bearer " + token)
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
                .header("Authorization", "Bearer " + token)
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
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();

        assertThatThrownBy(() ->
                restClient.get()
                .uri("/transactions/" + transactionIdToDelete)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(Transaction.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }

    @Test
    void shouldUpdateTransactionById() {
        int transactionId = 1;
        shouldCreateTransaction();
        TransactionUpdateDTO dto = new TransactionUpdateDTO(TransactionStatus.ACCEPTED);

        restClient.put()
                .uri("/transactions/" + transactionId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .toBodilessEntity();

        final TransactionDTO fetched = restClient.get()
                .uri("/transactions/" + transactionId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(TransactionDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getStatus()).isEqualTo(TransactionStatus.ACCEPTED);
    }
}
