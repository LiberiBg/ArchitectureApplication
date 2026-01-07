package esiea.yangnguyen.architectureapplication.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class Transaction {
    private long id;
    private User requester;
    private User recipient;
    private List<Product> offeredProducts;
    private List<Product> requestedProducts;
    private TransactionStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime acceptanceDate;
    private LocalDateTime closingDate;

    public Transaction(long id, User requester, User recipient, List<Product> offeredProducts, List<Product> requestedProducts, TransactionStatus status, LocalDateTime creationDate, LocalDateTime acceptanceDate, LocalDateTime closingDate) {
        this.id = id;
        this.requester = requester;
        this.recipient = recipient;
        this.offeredProducts = offeredProducts;
        this.requestedProducts = requestedProducts;
        this.status = status;
        this.creationDate = creationDate;
        this.acceptanceDate = acceptanceDate;
        this.closingDate = closingDate;
    }

    public Transaction(User requester, User recipient, List<Product> offeredProducts, List<Product> requestedProducts) {
        this.requester = requester;
        this.recipient = recipient;
        this.offeredProducts = offeredProducts;
        this.requestedProducts = requestedProducts;
    }
}