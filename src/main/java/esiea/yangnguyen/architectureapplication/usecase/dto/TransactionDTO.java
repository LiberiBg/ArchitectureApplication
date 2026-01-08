package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TransactionDTO {
    private long id;
    private long requesterId;
    private long recipientId;
    private List<Long> offeredProducts;
    private List<Long> requestedProducts;
    private TransactionStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime acceptanceDate;
    private LocalDateTime closingDate;

    public TransactionDTO(
            long id,
            long requesterId,
            long recipientId,
            List<Long> offeredProducts,
            List<Long> requestedProducts,
            TransactionStatus status,
            LocalDateTime creationDate,
            LocalDateTime acceptanceDate,
            LocalDateTime closingDate
    ) {
        this.id = id;
        this.requesterId = requesterId;
        this.recipientId = recipientId;
        this.offeredProducts = offeredProducts;
        this.requestedProducts = requestedProducts;
        this.status = status;
        this.creationDate = creationDate;
        this.acceptanceDate = acceptanceDate;
        this.closingDate = closingDate;
    }
}
