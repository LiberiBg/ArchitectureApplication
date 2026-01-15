package esiea.yangnguyen.architectureapplication.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedProductEvent {
    private long id;
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
