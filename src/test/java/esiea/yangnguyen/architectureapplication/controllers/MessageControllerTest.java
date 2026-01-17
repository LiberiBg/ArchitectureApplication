package esiea.yangnguyen.architectureapplication.controllers;

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
class MessageControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    private String token;
    private Long userId1;
    private Long userId2;

    @BeforeEach
    void setup() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        String uniqueEmail1 = "user1" + System.currentTimeMillis() + "@mail.com";
        UserCreateDTO userCreateDTO1 = new UserCreateDTO("User1", "Test", uniqueEmail1, "Test1234!");
        UserDTO createdUser1 = restClient.post()
                .uri("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(userCreateDTO1)
                .retrieve()
                .body(UserDTO.class);
        userId1 = createdUser1.getId();

        String uniqueEmail2 = "user2" + System.currentTimeMillis() + "@mail.com";
        UserCreateDTO userCreateDTO2 = new UserCreateDTO("User2", "Test", uniqueEmail2, "Test1234!");
        UserDTO createdUser2 = restClient.post()
                .uri("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(userCreateDTO2)
                .retrieve()
                .body(UserDTO.class);
        userId2 = createdUser2.getId();

        UserAuthDTO userAuthDTO = new UserAuthDTO(uniqueEmail1, "Test1234!");
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
    void shouldSendMessage() {
        MessageInDTO dto = new MessageInDTO(userId1, userId2, "Hello, this is a test message");

        MessageOutDTO created = restClient.post()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(MessageOutDTO.class);

        assertThat(created).isNotNull();
        assertThat(created.getIdSender()).isEqualTo(userId1);
        assertThat(created.getIdReceiver()).isEqualTo(userId2);
        assertThat(created.getContent()).isEqualTo("Hello, this is a test message");
        assertThat(created.getTimestamp()).isNotNull();
    }

    @Test
    void shouldGetAllMessages() {
        // Create a message first
        MessageInDTO dto = new MessageInDTO(userId1, userId2, "Test message for getAll");
        restClient.post()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(MessageOutDTO.class);

        List<MessageOutDTO> messages = restClient.get()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertThat(messages).isNotNull();
    }

    @Test
    void shouldGetMessageById() {
        // Create a message first
        MessageInDTO dto = new MessageInDTO(userId1, userId2, "Test message for getById");
        MessageOutDTO created = restClient.post()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(MessageOutDTO.class);

        MessageOutDTO fetched = restClient.get()
                .uri("/messages/" + created.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(MessageOutDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(created.getId());
        assertThat(fetched.getContent()).isEqualTo("Test message for getById");
        assertThat(fetched.getIdSender()).isEqualTo(userId1);
        assertThat(fetched.getIdReceiver()).isEqualTo(userId2);
    }

    @Test
    void shouldGetMessagesBySenderId() {
        // Create multiple messages from the same sender
        MessageInDTO dto1 = new MessageInDTO(userId1, userId2, "Message 1 from sender");
        restClient.post()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto1)
                .retrieve()
                .body(MessageOutDTO.class);

        MessageInDTO dto2 = new MessageInDTO(userId1, userId2, "Message 2 from sender");
        restClient.post()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto2)
                .retrieve()
                .body(MessageOutDTO.class);

        List<MessageOutDTO> messages = restClient.get()
                .uri("/messages/sender/" + userId1)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertThat(messages).isNotNull();
        assertThat(messages.size()).isGreaterThanOrEqualTo(2);
        messages.forEach(message -> assertThat(message.getIdSender()).isEqualTo(userId1));
    }

    @Test
    void shouldGetMessagesByReceiverId() {
        // Create multiple messages to the same receiver
        MessageInDTO dto1 = new MessageInDTO(userId1, userId2, "Message 1 to receiver");
        restClient.post()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto1)
                .retrieve()
                .body(MessageOutDTO.class);

        MessageInDTO dto2 = new MessageInDTO(userId1, userId2, "Message 2 to receiver");
        restClient.post()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto2)
                .retrieve()
                .body(MessageOutDTO.class);

        List<MessageOutDTO> messages = restClient.get()
                .uri("/messages/receiver/" + userId2)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertThat(messages).isNotNull();
        assertThat(messages.size()).isGreaterThanOrEqualTo(2);
        messages.forEach(message -> assertThat(message.getIdReceiver()).isEqualTo(userId2));
    }

    @Test
    void shouldDeleteMessageById() {
        // Create a message first
        MessageInDTO dto = new MessageInDTO(userId1, userId2, "Message to delete");
        MessageOutDTO created = restClient.post()
                .uri("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(MessageOutDTO.class);

        long messageIdToDelete = created.getId();

        restClient.delete()
                .uri("/messages/" + messageIdToDelete)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();

        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/messages/" + messageIdToDelete)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .body(MessageOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }

    @Test
    void shouldReturn404WhenMessageNotFound() {
        long nonExistentId = 99999L;

        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/messages/" + nonExistentId)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .body(MessageOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }

    @Test
    void shouldReturn401WhenUnauthorized() {
        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/messages/1")
                        .retrieve()
                        .body(MessageOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.Unauthorized.class);
    }
}
