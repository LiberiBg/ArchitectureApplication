package esiea.yangnguyen.architectureapplication.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserAuthDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserInDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserOutDTO;
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
    void shouldCreateUser() {
        UserInDTO dto = new UserInDTO("Toto", "Tata", "toto@mail.com", "Test1234!");

        UserOutDTO created = restClient.post()
                .uri("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(UserOutDTO.class);

        assertThat(created).isNotNull();
        assertThat(created.getFirstName()).isEqualTo("Toto");
    }

    @Test
    void shouldGetUserById() {
        final User expected = new User(1,"Alice", "Dupont", "alice.dupont@test.com", "Password1234!");

        final UserOutDTO fetched = restClient.get()
                .uri("/users/" + expected.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(UserOutDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(expected.getId());
        assertThat(fetched.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(fetched.getLastname()).isEqualTo(expected.getLastName());
        assertThat(fetched.getEmail()).isEqualTo(expected.getEmail());
    }

    @Test
    void shouldLoginUser() {
        String uniqueEmail = "login" + System.currentTimeMillis() + "@mail.com";
        UserInDTO userInDTO = new UserInDTO("Login", "Test", uniqueEmail, "Test1234!");
        restClient.post()
                .uri("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(userInDTO)
                .retrieve()
                .body(UserOutDTO.class);

        UserAuthDTO userAuthDTO = new UserAuthDTO(uniqueEmail, "Test1234!");
        Map<String, String> response = restClient.post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(userAuthDTO)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertThat(response).isNotNull();
        assertThat(response.get("token")).isNotNull();
        assertThat(response.get("token")).isNotEmpty();
    }

    @Test
    void shouldGetAllUsers() {
        List<UserOutDTO> users = restClient.get()
                .uri("/users")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
    }

    @Test
    void shouldUpdateUserById() {
        long userId = 1;
        UserInDTO updateDTO = new UserInDTO("Updated", "Name", "updated@mail.com", "NewPassword123!");

        restClient.put()
                .uri("/users/" + userId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateDTO)
                .retrieve()
                .toBodilessEntity();

        UserOutDTO updated = restClient.get()
                .uri("/users/" + userId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(UserOutDTO.class);

        assertThat(updated).isNotNull();
        assertThat(updated.getFirstName()).isEqualTo("Updated");
        assertThat(updated.getLastname()).isEqualTo("Name");
        assertThat(updated.getEmail()).isEqualTo("updated@mail.com");
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        long nonExistentId = 99999L;

        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/users/" + nonExistentId)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .body(UserOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }

    @Test
    void shouldReturn401WhenUnauthorized() {
        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/users/1")
                        .retrieve()
                        .body(UserOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.Unauthorized.class);
    }

    @Test
    void shouldDeleteUserById() {
        long userIdToDelete = 4;

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
