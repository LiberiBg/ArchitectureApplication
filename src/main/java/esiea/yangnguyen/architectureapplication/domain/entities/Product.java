package esiea.yangnguyen.architectureapplication.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private long id;
    private String name;
    private String description;
    private String brand;
    private State state;
    private String size;
    private String category;
    private String season;
    private int score;
    private long providerId;
    private ProductStatus status;
}
