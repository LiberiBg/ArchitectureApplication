package esiea.yangnguyen.architectureapplication.usecase.dto;

import java.util.List;

public class TransactionCreateDTO {
    private long requesterId;
    private long recipientId;
    private List<Long> offeredClothes;
    private List<Long> requestedClothes;

    public TransactionCreateDTO(long requesterId, long recipientId, List<Long> offeredClothes, List<Long> requestedClothes) {
        this.requesterId = requesterId;
        this.recipientId = recipientId;
        this.offeredClothes = offeredClothes;
        this.requestedClothes = requestedClothes;
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
}
