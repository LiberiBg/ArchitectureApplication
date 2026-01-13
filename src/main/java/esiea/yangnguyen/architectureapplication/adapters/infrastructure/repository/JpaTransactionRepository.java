package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaTransactionEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper.JpaTransactionMapper;
import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import esiea.yangnguyen.architectureapplication.domain.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class JpaTransactionRepository implements TransactionRepository {

    private final SpringDataTransactionRepository springDataTransactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        JpaTransactionEntity jpaTransactionEntity = JpaTransactionMapper.toEntity(transaction);
        return JpaTransactionMapper.toDomain(springDataTransactionRepository.save(jpaTransactionEntity));
    }

    @Override
    public Optional<Transaction> findById(long id) {
        return springDataTransactionRepository.findById(id).map(JpaTransactionMapper::toDomain);
    }

    @Override
    public List<Transaction> findAll() {
        return springDataTransactionRepository.findAll()
                .stream().map(JpaTransactionMapper::toDomain)
                .toList();
    }

    @Override
    public void updateById(long id, TransactionStatus status) {
        JpaTransactionEntity jpaTransactionEntity = springDataTransactionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        jpaTransactionEntity.setStatus(status);
        springDataTransactionRepository.save(jpaTransactionEntity);
    }

    @Override
    public void deleteById(Long id) {
        springDataTransactionRepository.deleteById(id);
    }

}
