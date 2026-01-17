package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.TransactionRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.domain.exceptions.TransactionNotFoundException;
import esiea.yangnguyen.architectureapplication.domain.exceptions.UnauthorizedException;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionInDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionUpdateDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.TransactionMapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import static esiea.yangnguyen.architectureapplication.domain.service.TransactionService.validateStatus;

@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Transaction createTransaction(TransactionInDTO transactionCreateDTO) {
        return transactionRepository.save(TransactionMapper.toDomain(transactionCreateDTO, userRepository, productRepository));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public void updateTransactionById(Long id, TransactionUpdateDTO transactionUpdateDTO) {
        Transaction transaction = getTransactionById(id)
                .orElseThrow(TransactionNotFoundException::new);

        if (!validateStatus(transaction, transactionUpdateDTO.getStatus()))
            throw new UnauthorizedException("Transition status update not authorized");

        transactionRepository.updateById(id, transactionUpdateDTO.getStatus());
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transactionRepository.deleteById(id);
    }
}