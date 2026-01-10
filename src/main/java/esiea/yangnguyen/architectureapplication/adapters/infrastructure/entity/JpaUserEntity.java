package esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor  // Génère no-args pour JPA
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // Tous args privé pour builder
@Builder(toBuilder = true)
public class JpaUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
}
