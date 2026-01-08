package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.TransactionRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.exceptions.Unauthorized;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionStatusUpdateDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Transaction createTransaction(TransactionCreateDTO transactionCreateDTO) {
        return transactionRepository.save(TransactionMapper.toDomain(transactionCreateDTO, userRepository, productRepository));
    }

    public Optional<TransactionDTO> getTransactionById(long id) {
        return transactionRepository.findById(id).map(TransactionMapper::toDTO);
    }

    public void updateStatus(TransactionStatusUpdateDTO transactionStatusUpdateDTO, long userId) {
        TransactionDTO transactionDTO = getTransactionById(transactionStatusUpdateDTO.getTransactionId())
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!statusUpdateAuthorized(transactionDTO, user, transactionStatusUpdateDTO.getStatus()))
            throw new Unauthorized("Transition status update not authorized");

        transactionRepository.updateStatus(transactionStatusUpdateDTO.getTransactionId(), transactionStatusUpdateDTO.getStatus());
    }

    private boolean statusUpdateAuthorized(TransactionDTO transactionDTO, User user, TransactionStatus newStatus) {
        // Requester can : PENDING -> CANCELLED, ACCEPTED/REJECTED -> REQUESTER_COMPLETED
        if (user.getId() == transactionDTO.getRequesterId()) {
            return switch (transactionDTO.getStatus()) {
                case PENDING -> newStatus == TransactionStatus.CANCELLED;
                case ACCEPTED, REJECTED -> newStatus == TransactionStatus.REQUESTER_COMPLETED;
                default -> false;
            };
        }

        // Recipient can : PENDING -> ACCEPTED/REJECTED, ACCEPTED/REJECTED -> RECIPIENT_COMPLETED
        if (user.getId() == transactionDTO.getRecipientId()) {
            return switch (transactionDTO.getStatus()) {
                case PENDING -> newStatus == TransactionStatus.ACCEPTED || newStatus == TransactionStatus.REJECTED;
                case ACCEPTED, REJECTED -> newStatus == TransactionStatus.RECIPIENT_COMPLETED;
                default -> false;
            };
        }

        return false;
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transactionRepository.deleteById(id);
    }
}