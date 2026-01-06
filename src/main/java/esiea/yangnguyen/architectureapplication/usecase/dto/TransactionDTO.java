package esiea.yangnguyen.architectureapplication.usecase.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionDTO {
    private long id;
    private long requesterId;
    private long recipientId;
    private List<Long> offeredClothes;
    private List<Long> requestedClothes;
    private String status;
    private LocalDateTime creationDate;
    private LocalDateTime acceptanceDate;
    private LocalDateTime closingDate;

    public TransactionDTO(
            long id,
            long requesterId,
            long recipientId,
            List<Long> offeredClothes,
            List<Long> requestedClothes,
            String status,
            LocalDateTime creationDate,
            LocalDateTime acceptanceDate,
            LocalDateTime closingDate
    ) {
        this.id = id;
        this.requesterId = requesterId;
        this.recipientId = recipientId;
        this.offeredClothes = offeredClothes;
        this.requestedClothes = requestedClothes;
        this.status = status;
        this.creationDate = creationDate;
        this.acceptanceDate = acceptanceDate;
        this.closingDate = closingDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(long requesterId) {
        this.requesterId = requesterId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public List<Long> getOfferedClothes() {
        return offeredClothes;
    }

    public void setOfferedClothes(List<Long> offeredClothes) {
        this.offeredClothes = offeredClothes;
    }

    public List<Long> getRequestedClothes() {
        return requestedClothes;
    }

    public void setRequestedClothes(List<Long> requestedClothes) {
        this.requestedClothes = requestedClothes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(LocalDateTime acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }
}
