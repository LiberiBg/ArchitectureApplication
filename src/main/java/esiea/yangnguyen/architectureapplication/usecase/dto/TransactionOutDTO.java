package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class TransactionOutDTO {
    private long id;
    private long requesterId;
    private long recipientId;
    private List<Long> offeredProducts;
    private List<Long> requestedProducts;
    private TransactionStatus status;
}
