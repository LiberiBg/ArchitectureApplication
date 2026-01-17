package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.TransactionRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.domain.exceptions.TransactionNotFoundException;
import esiea.yangnguyen.architectureapplication.domain.exceptions.UnauthorizedException;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionDTO;
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

    public TransactionDTO createTransaction(TransactionCreateDTO transactionCreateDTO) {
        return TransactionMapper.toDTO(transactionRepository.save(TransactionMapper.toDomain(transactionCreateDTO, userRepository, productRepository)));
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream().map(TransactionMapper::toDTO).toList();
    }

    public Optional<TransactionDTO> getTransactionById(Long id) {
        return transactionRepository.findById(id).map(TransactionMapper::toDTO);
    }

    public void updateTransactionById(Long id, TransactionUpdateDTO transactionUpdateDTO) {
        TransactionDTO transactionDTO = getTransactionById(id)
                .orElseThrow(TransactionNotFoundException::new);

        if (!validateStatus(transactionDTO, transactionUpdateDTO.getStatus()))
            throw new UnauthorizedException("Transition status update not authorized");

        transactionRepository.updateById(id, transactionUpdateDTO.getStatus());
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transactionRepository.deleteById(id);
    }
}