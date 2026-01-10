package esiea.yangnguyen.architectureapplication.adapters.controllers;

import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionStatusUpdateDTO;
import esiea.yangnguyen.architectureapplication.usecase.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionCreateDTO transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
    }

    @PutMapping("/status")
    public void updateStatus(@RequestBody TransactionStatusUpdateDTO transactionStatusUpdateDTO) {
        transactionService.updateStatus(transactionStatusUpdateDTO);
    }
}
