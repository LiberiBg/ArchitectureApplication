package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(long id);
    List<Transaction> findAllByRequesterId(long id);
    void updateStatus(long id, TransactionStatus status);
}
