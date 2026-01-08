package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class TransactionDTO {
    private long id;
    private long requesterId;
    private long recipientId;
    private List<Long> offeredClothes;
    private List<Long> requestedClothes;
    private TransactionStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime acceptanceDate;
    private LocalDateTime closingDate;
}
