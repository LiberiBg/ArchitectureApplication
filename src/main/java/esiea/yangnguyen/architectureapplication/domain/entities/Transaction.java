package esiea.yangnguyen.architectureapplication.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {
    private long id;
    private User requester;
    private User recipient;
    private List<Product> offeredProducts;
    private List<Product> requestedProducts;
    private TransactionStatus status;

    public Transaction(User requester, User recipient, List<Product> offeredProducts, List<Product> requestedProducts) {
        this.requester = requester;
        this.recipient = recipient;
        this.offeredProducts = offeredProducts;
        this.requestedProducts = requestedProducts;
    }
}