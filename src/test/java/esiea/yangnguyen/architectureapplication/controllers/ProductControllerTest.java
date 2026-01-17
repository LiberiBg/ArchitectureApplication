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
        ProductInDTO dto = new ProductInDTO("Nike Air", "Comfortable running shoes", "Nike",
                State.NEW, "42", "Sportswear", "Summer", 1, ProductStatus.AVAILABLE);

        ProductOutDTO created = restClient.post()
                .uri("/products")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(ProductOutDTO.class);

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("Nike Air");
    }

    @Test
    void shouldGetProductById() {
        final Product expected = new Product(2,
                "Adidas Stan Smith",
                "Chaussures en cuir blanc",
                "Adidas",
                State.NEW,
                "43",
                "Casual",
                "Spring",
                92,
                1,
                ProductStatus.AVAILABLE);

        final ProductOutDTO fetched = restClient.get()
                .uri("/products/" + expected.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(ProductOutDTO.class);

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
    void shouldGetAllProducts() {
        List<ProductOutDTO> products = restClient.get()
                .uri("/products")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertThat(products).isNotNull();
    }

    @Test
    void shouldUpdateProductById() {
        long productId = 1;
        ProductInDTO updateDTO = new ProductInDTO("Updated Product", "Updated description", "Updated Brand",
                State.POOR, "43", "Updated Category", "Spring", 1, ProductStatus.AVAILABLE);

        restClient.put()
                .uri("/products/" + productId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateDTO)
                .retrieve()
                .toBodilessEntity();

        ProductOutDTO updated = restClient.get()
                .uri("/products/" + productId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(ProductOutDTO.class);

        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("Updated Product");
        assertThat(updated.getDescription()).isEqualTo("Updated description");
        assertThat(updated.getBrand()).isEqualTo("Updated Brand");
    }

    @Test
    void shouldReturn404WhenProductNotFound() {
        long nonExistentId = 99999L;

        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/products/" + nonExistentId)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .body(ProductOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }

    @Test
    void shouldReturn401WhenUnauthorized() {
        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/products/1")
                        .retrieve()
                        .body(ProductOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.Unauthorized.class);
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
                .body(ProductOutDTO.class)
        ).isInstanceOf(HttpClientErrorException.NotFound.class);
    }
}
