package esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity;

import esiea.yangnguyen.architectureapplication.domain.entities.ProductStatus;
import esiea.yangnguyen.architectureapplication.domain.entities.State;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor  // Génère no-args pour JPA
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // Tous args privé pour builder
@Builder(toBuilder = true)
public class JpaProductEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
