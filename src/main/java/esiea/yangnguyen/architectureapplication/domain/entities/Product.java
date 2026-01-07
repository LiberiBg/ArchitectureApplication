package esiea.yangnguyen.architectureapplication.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public Product(
            long id,
            String name,
            String description,
            String brand,
            State state,
            String size,
            String category,
            String season,
            int score,
            long providerId,
            ProductStatus status
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.state = state;
        this.size = size;
        this.category = category;
        this.season = season;
        this.score = score;
        this.providerId = providerId;
        this.status = status;
    }

}
