package esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity;

import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor  // Génère no-args pour JPA
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // Tous args privé pour builder
@Builder(toBuilder = true)
public class JpaTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private JpaUserEntity requester;
    @ManyToOne(fetch = FetchType.LAZY)
    private JpaUserEntity recipient;
    @ManyToMany
    private List<JpaProductEntity> offeredProducts = new ArrayList<>();
    @ManyToMany
    private List<JpaProductEntity> requestedProducts = new ArrayList<>();
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Column(nullable = false, updatable = false)

    @PrePersist
    void onCreate() {
        if (status == null) {
            status = TransactionStatus.PENDING;
        }
    }
}
