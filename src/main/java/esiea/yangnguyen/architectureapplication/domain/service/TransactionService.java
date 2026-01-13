package esiea.yangnguyen.architectureapplication.domain.service;

import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionService {
//    public static boolean validateStatus(TransactionDTO transactionDTO, User user, TransactionStatus newStatus) {
//        // Requester can : PENDING -> CANCELLED, ACCEPTED/REJECTED -> REQUESTER_COMPLETED
//        if (user.getId() == transactionDTO.getRequesterId()) {
//            return switch (transactionDTO.getStatus()) {
//                case PENDING -> newStatus == TransactionStatus.CANCELLED;
//                case ACCEPTED, REJECTED -> newStatus == TransactionStatus.REQUESTER_COMPLETED;
//                default -> false;
//            };
//        }
//
//        // Recipient can : PENDING -> ACCEPTED/REJECTED, ACCEPTED/REJECTED -> RECIPIENT_COMPLETED
//        if (user.getId() == transactionDTO.getRecipientId()) {
//            return switch (transactionDTO.getStatus()) {
//                case PENDING -> newStatus == TransactionStatus.ACCEPTED || newStatus == TransactionStatus.REJECTED;
//                case ACCEPTED, REJECTED -> newStatus == TransactionStatus.RECIPIENT_COMPLETED;
//                default -> false;
//            };
//        }
//
//        return false;
//    }

    public static boolean validateStatus(TransactionDTO transactionDTO, TransactionStatus newStatus) {
        return switch (transactionDTO.getStatus()) {
            case PENDING -> newStatus == TransactionStatus.CANCELLED || newStatus == TransactionStatus.ACCEPTED || newStatus == TransactionStatus.REJECTED;
            case ACCEPTED, REJECTED -> newStatus == TransactionStatus.COMPLETED;
            default -> false;
        };
    }
}
