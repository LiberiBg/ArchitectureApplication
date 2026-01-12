package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserRepository extends JpaRepository<JpaUserEntity, Long> {
    Optional<JpaUserEntity> findByEmail(String email);
}
