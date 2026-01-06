package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private String brand;
    private String state;
    private String size;
    private String category;
    private String season;
    private int score;
    private long providerId;
    private Status status;

    public ProductDTO(
            long id,
            String name,
            String description,
            String brand,
            String state,
            String size,
            String category,
            String season,
            int score,
            long providerId,
            Status status
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
