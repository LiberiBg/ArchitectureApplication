package esiea.yangnguyen.architectureapplication.domain.service;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionService {

    public static boolean validateStatus(Transaction transaction, TransactionStatus newStatus) {
        return switch (transaction.getStatus()) {
            case PENDING ->
                    newStatus == TransactionStatus.CANCELLED || newStatus == TransactionStatus.ACCEPTED || newStatus == TransactionStatus.REJECTED;
            case ACCEPTED, REJECTED -> newStatus == TransactionStatus.COMPLETED;
            default -> false;
        };
    }
}
