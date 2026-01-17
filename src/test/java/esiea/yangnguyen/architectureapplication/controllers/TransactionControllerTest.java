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
import org.springframework.context.annotation.Import;
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
        "springdoc.swagger-ui.enabled=false",
        "spring.sql.init.mode=always",
        "spring.sql.init.data-locations=classpath:data-test.sql",
        "spring.sql.init.schema-locations=",
        "spring.jpa.defer-datasource-initialization=true"
})
@Import(esiea.yangnguyen.architectureapplication.config.TestConfig.class)
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
        UserInDTO userInDTO = new UserInDTO("Test", "Test", uniqueEmail, "Test1234!");
        restClient.post()
                .uri("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(userInDTO)
                .retrieve()
                .body(UserOutDTO.class);

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
        TransactionInDTO dto = new TransactionInDTO(1, 2, List.of(), List.of());

        TransactionOutDTO created = restClient.post()
                .uri("/transactions")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(TransactionOutDTO.class);

        assertThat(created).isNotNull();
        assertThat(created.getRequesterId()).isEqualTo(1);
    }

    @Test
    void shouldGetTransactionById() {
        long transactionId = 3;

        shouldCreateTransaction();
        final TransactionOutDTO fetched = restClient.get()
                .uri("/transactions/" + transactionId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(TransactionOutDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(transactionId);
        assertThat(fetched.getRequesterId()).isEqualTo(4);
        assertThat(fetched.getRecipientId()).isEqualTo(1);
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
    void shouldGetAllTransactions() {
        List<TransactionOutDTO> transactions = restClient.get()
                .uri("/transactions")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertThat(transactions).isNotNull();
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

        final TransactionOutDTO fetched = restClient.get()
                .uri("/transactions/" + transactionId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(TransactionOutDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getStatus()).isEqualTo(TransactionStatus.ACCEPTED);
    }

    @Test
    void shouldReturn404WhenTransactionNotFound() {
        long nonExistentId = 99999L;

        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/transactions/" + nonExistentId)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .body(TransactionOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }

    @Test
    void shouldReturn401WhenUnauthorized() {
        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/transactions/1")
                        .retrieve()
                        .body(TransactionOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.Unauthorized.class);
    }
}
