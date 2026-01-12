package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMessageRepository extends JpaRepository<JpaMessageEntity, Long> {
}
