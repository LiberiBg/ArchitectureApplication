package esiea.yangnguyen.architectureapplication.usecase.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionInDTO {
    private long requesterId;
    private long recipientId;
    private List<Long> offeredProducts;
    private List<Long> requestedProducts;

    public TransactionInDTO(long requesterId, long recipientId, List<Long> offeredProducts, List<Long> requestedProducts) {
        this.requesterId = requesterId;
        this.recipientId = recipientId;
        this.offeredProducts = offeredProducts;
        this.requestedProducts = requestedProducts;
    }
}