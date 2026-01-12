package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.State;
import esiea.yangnguyen.architectureapplication.domain.entities.ProductStatus;
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
}
