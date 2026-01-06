package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;

public class TransactionStatusUpdateDTO {
    private long transactionId;
    private TransactionStatus status;

    public TransactionStatusUpdateDTO(long transactionId, long userId, TransactionStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
