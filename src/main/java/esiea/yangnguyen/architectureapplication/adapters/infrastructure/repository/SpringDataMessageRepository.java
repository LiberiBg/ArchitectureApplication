package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaMessageEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMessageRepository extends JpaRepository<JpaMessageEntity, Long> {
    List<JpaMessageEntity> findBySender(JpaUserEntity sender);
    List<JpaMessageEntity> findByReceiver(JpaUserEntity receiver);
}
