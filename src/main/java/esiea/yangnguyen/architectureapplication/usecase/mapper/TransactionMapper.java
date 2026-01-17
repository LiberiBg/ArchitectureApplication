package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionInDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionOutDTO;

import java.util.List;

public class TransactionMapper {
    public static Transaction toDomain(TransactionInDTO transactionCreateDTO, UserRepository userRepository, ProductRepository productRepository) {
        User requester = userRepository.findById(transactionCreateDTO.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + transactionCreateDTO.getRequesterId()));
        User recipient = userRepository.findById(transactionCreateDTO.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + transactionCreateDTO.getRecipientId()));

        List<Product> offeredProduct = transactionCreateDTO.getOfferedProducts().stream().map(
                product -> productRepository.findById(product)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + transactionCreateDTO.getRecipientId()))
        ).toList();
        List<Product> requestedProducts = transactionCreateDTO.getRequestedProducts().stream().map(
                product -> productRepository.findById(product)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + transactionCreateDTO.getRecipientId()))
        ).toList();

        return new Transaction(
                requester,
                recipient,
                offeredProduct,
                requestedProducts
        );
    }

    public static TransactionOutDTO toDTO(Transaction transaction) {
        return new TransactionOutDTO(
                transaction.getId(),
                transaction.getRequester().getId(),
                transaction.getRecipient().getId(),
                transaction.getOfferedProducts().stream().map(Product::getId).toList(),
                transaction.getRequestedProducts().stream().map(Product::getId).toList(),
                transaction.getStatus()
        );
    }
}