package esiea.yangnguyen.architectureapplication.domain.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private long id;
    private User requester;
    private User recipient;
    private List<Long> offeredClothes;
    private List<Long> requestedClothes;
    private TransactionStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime acceptanceDate;
    private LocalDateTime closingDate;

    public Transaction(long id, User requester, User recipient, List<Long> offeredClothes, List<Long> requestedClothes, TransactionStatus status, LocalDateTime creationDate, LocalDateTime acceptanceDate, LocalDateTime closingDate) {
        this.id = id;
        this.requester = requester;
        this.recipient = recipient;
        this.offeredClothes = offeredClothes;
        this.requestedClothes = requestedClothes;
        this.status = status;
        this.creationDate = creationDate;
        this.acceptanceDate = acceptanceDate;
        this.closingDate = closingDate;
    }

    public Transaction(User requester, User recipient, List<Long> offeredClothes, List<Long> requestedClothes) {
        this.requester = requester;
        this.recipient = recipient;
        this.offeredClothes = offeredClothes;
        this.requestedClothes = requestedClothes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
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

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
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