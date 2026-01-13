package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository {
    Transaction save(Transaction transaction);

    List<Transaction> findAll();

    Optional<Transaction> findById(long id);

    void updateById(long id, TransactionStatus status);

    void deleteById(Long id);
}
