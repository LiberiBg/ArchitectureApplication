package esiea.yangnguyen.architectureapplication.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "springdoc.api-docs.enabled=false",  // Disable Swagger en test
        "springdoc.swagger-ui.enabled=false"
})
class UserControllerTest {

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
    void shouldCreateUser() {
        UserCreateDTO dto = new UserCreateDTO("Toto", "Tata", "toto@mail.com", "Test1234!");

        UserDTO created = restClient.post()
                .uri("/users")
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
                .retrieve()
                .toBodilessEntity();

        User fetched = restClient.get()
                .uri("/users/" + userIdToDelete)
                .retrieve()
                .body(User.class);

        assertThat(fetched).isNull();
    }
}
