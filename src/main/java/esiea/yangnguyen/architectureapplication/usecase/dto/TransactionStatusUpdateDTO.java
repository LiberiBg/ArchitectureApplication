package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatusUpdateDTO {
    private long transactionId;
    private TransactionStatus status;

    public TransactionStatusUpdateDTO(long transactionId, TransactionStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }
}
