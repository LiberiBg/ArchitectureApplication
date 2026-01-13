package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.ProductStatus;
import esiea.yangnguyen.architectureapplication.domain.entities.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor  // Génère no-args pour JPA
@Getter
public class ProductCreateDTO {
    private String name;
    private String description;
    private String brand;
    private State state;
    private String size;
    private String category;
    private String season;
    private long providerId;
    private ProductStatus status;

    public ProductCreateDTO(String name, String description, String brand, State state, String size, String category, String season, long providerId) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.state = state;
        this.size = size;
        this.category = category;
        this.season = season;
        this.providerId = providerId;
    }
}
