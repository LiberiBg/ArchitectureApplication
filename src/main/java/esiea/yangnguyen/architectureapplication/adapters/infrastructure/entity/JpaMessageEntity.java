package esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "messages")
@Getter
@NoArgsConstructor  // Génère no-args pour JPA
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // Tous args privé pour builder
@Builder(toBuilder = true)
public class JpaMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private JpaUserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private JpaUserEntity receiver;
    private String content;
    private String timestamp;
}
