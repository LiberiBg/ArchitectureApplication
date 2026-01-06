package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.TransactionRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.TransactionMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(TransactionCreateDTO dto) {
        User requester = userRepository.findById(dto.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("Requester not found"));

        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        Transaction transaction = TransactionMapper.toDomain(dto, requester, recipient);

        return transactionRepository.save(transaction);
    }

    public Optional<TransactionDTO> getTransactionById(long id) {
        return transactionRepository.findById(id).map(TransactionMapper::toDTO);
    }

    public List<TransactionDTO> getAllByRequesterId(long requesterId) {
        return transactionRepository.findAllByRequesterId(requesterId).stream().map(TransactionMapper::toDTO).collect(Collectors.toList());
    }
}
