package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.State;
import esiea.yangnguyen.architectureapplication.domain.entities.Status;
import lombok.Getter;

public class PostProductDTO {

    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private String brand;

    @Getter
    private State state;

    @Getter
    private String size;

    @Getter
    private String category;

    @Getter
    private String season;

    @Getter
    private int score;

    @Getter
    private long providerId;

    @Getter
    private Status status;

    public PostProductDTO(Long id, String name, String description, String brand, State state, String size, String category, String season, long providerId, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.state = state;
        this.size = size;
        this.category = category;
        this.season = season;
        this.providerId = providerId;
        this.status = status;
    }
}
