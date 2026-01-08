package esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaTransactionEntity;
import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JpaTransactionMapper {
    public static Transaction toDomain(JpaTransactionEntity transactionEntity) {
        return new Transaction(
                transactionEntity.getId(),
                JpaUserMapper.toDomain(transactionEntity.getRequester()),
                JpaUserMapper.toDomain(transactionEntity.getRecipient()),
                transactionEntity.getOfferedProducts().stream().map(JpaProductMapper::toDomain).toList(),
                transactionEntity.getOfferedProducts().stream().map(JpaProductMapper::toDomain).toList(),
                transactionEntity.getStatus(),
                transactionEntity.getCreationDate(),
                transactionEntity.getAcceptanceDate(),
                transactionEntity.getClosingDate()
        );
    }

    public static JpaTransactionEntity toEntity(Transaction transaction) {
        return JpaTransactionEntity.builder()
                .id(transaction.getId())
                .requester(JpaUserMapper.toEntity(transaction.getRequester()))
                .recipient(JpaUserMapper.toEntity(transaction.getRecipient()))
                .offeredProducts(transaction.getOfferedProducts().stream().map(JpaProductMapper::toEntity).toList())
                .requestedProducts(transaction.getOfferedProducts().stream().map(JpaProductMapper::toEntity).toList())
                .status(transaction.getStatus())
                .creationDate(transaction.getCreationDate())
                .acceptanceDate(transaction.getAcceptanceDate())
                .closingDate(transaction.getClosingDate())
                .build();
    }
}
