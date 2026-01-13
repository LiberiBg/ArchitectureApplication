package esiea.yangnguyen.architectureapplication.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;

import esiea.yangnguyen.architectureapplication.domain.entities.ProductStatus;
import esiea.yangnguyen.architectureapplication.domain.entities.State;
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
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "springdoc.api-docs.enabled=false",  // Disable Swagger en test
        "springdoc.swagger-ui.enabled=false"
})
class ProductControllerTest {

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
    void shouldCreateProduct() {
        ProductCreateDTO dto = new ProductCreateDTO("Nike Air", "Comfortable running shoes", "Nike",
                State.NEW, "42", "Sportswear", "Summer", 1, ProductStatus.AVAILABLE);

        ProductDTO created = restClient.post()
                .uri("/products")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(ProductDTO.class);

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("Nike Air");
    }

    @Test
    void shouldGetProductById() {
        final Product expected = new Product(1,
                "Puma RS-X",
                "Chaussures streetwear modernes",
                "Puma",
                State.NEW,
                "44",
                "Casual",
                "Winter",
                88,
                456,
                ProductStatus.AVAILABLE);

        final ProductDTO fetched = restClient.get()
                .uri("/products/" + expected.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(ProductDTO.class);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(expected.getId());
        assertThat(fetched.getName()).isEqualTo(expected.getName());
        assertThat(fetched.getStatus()).isEqualTo(expected.getStatus());
        assertThat(fetched.getBrand()).isEqualTo(expected.getBrand());
        assertThat(fetched.getCategory()).isEqualTo(expected.getCategory());
        assertThat(fetched.getDescription()).isEqualTo(expected.getDescription());
        assertThat(fetched.getScore()).isEqualTo(expected.getScore());
        assertThat(fetched.getSize()).isEqualTo(expected.getSize());
        assertThat(fetched.getSeason()).isEqualTo(expected.getSeason());
    }

    @Test
    void shouldDeleteProductById() {
        long productIdToDelete = 1;

        restClient.delete()
                .uri("/products/" + productIdToDelete)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();

        assertThatThrownBy(() ->
                restClient.get()
                .uri("/products/" + productIdToDelete)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(ProductDTO.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }
}
