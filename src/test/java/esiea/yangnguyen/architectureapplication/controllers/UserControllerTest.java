package esiea.yangnguyen.architectureapplication.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserAuthDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "springdoc.api-docs.enabled=false",  // Disable Swagger en test
        "springdoc.swagger-ui.enabled=false"
})
class UserControllerTest {

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
    void shouldCreateUser() {
        UserCreateDTO dto = new UserCreateDTO("Toto", "Tata", "toto@mail.com", "Test1234!");

        UserDTO created = restClient.post()
                .uri("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(UserDTO.class);

        assertThat(created).isNotNull();
        assertThat(created.getFirstName()).isEqualTo("Toto");
    }

    @Test
    void shouldGetUserById() {
        final User expected = new User(1,"Titi", "Tata", "titi@mail.com", "Test1234!");

        final UserDTO fetched = restClient.get()
                .uri("/users/" + expected.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(UserDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(expected.getId());
        assertThat(fetched.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(fetched.getLastname()).isEqualTo(expected.getLastName());
        assertThat(fetched.getEmail()).isEqualTo(expected.getEmail());
    }

    @Test
    void shouldDeleteUserById() {
        long userIdToDelete = 1;

        restClient.delete()
                .uri("/users/" + userIdToDelete)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();

        assertThatThrownBy(() ->
                restClient.get()
                .uri("/users/" + userIdToDelete)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(User.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }
}
