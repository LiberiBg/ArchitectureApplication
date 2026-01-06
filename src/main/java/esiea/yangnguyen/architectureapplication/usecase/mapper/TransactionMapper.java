package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionDTO;

public class TransactionMapper {
    public static Transaction toDomain(TransactionCreateDTO transactionCreateDTO, User requester, User recipient) {
        return new Transaction(requester, recipient, transactionCreateDTO.getOfferedClothes(), transactionCreateDTO.getRequestedClothes());
    }

    public static TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getRequester().getId(),
                transaction.getRecipient().getId(),
                transaction.getOfferedClothes(),
                transaction.getRequestedClothes(),
                transaction.getStatus(),
                transaction.getCreationDate(),
                transaction.getAcceptanceDate(),
                transaction.getClosingDate()
        );
    }
}
