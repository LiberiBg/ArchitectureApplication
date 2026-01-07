package esiea.yangnguyen.architectureapplication.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "state")
    private State state;

    @Column(name = "size")
    private String size;

    @Column(name = "category")
    private String category;

    @Column(name = "season")
    private String season;

    @Column(name = "score")
    private int score;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private long provider;

    @Column(name = "status")
    private ProductStatus status;

    public Product(long id, String name, String description, String brand, State state, String size, String category,
                   String season, int score, long provider, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.state = state;
        this.size = size;
        this.category = category;
        this.season = season;
        this.score = score;
        this.provider = provider;
        this.status = status;
    }
}