package esiea.yangnguyen.architectureapplication.adapters.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionUpdateDTO;
import esiea.yangnguyen.architectureapplication.usecase.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionCreateDTO transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public void updateTransactionById(@PathVariable Long id, @RequestBody TransactionUpdateDTO transactionUpdateDTO) {
        transactionService.updateTransactionById(id, transactionUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
    }
}
