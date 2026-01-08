package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(long id);
    void updateStatus(long id, TransactionStatus status);
    void deleteById(Long id);
}
