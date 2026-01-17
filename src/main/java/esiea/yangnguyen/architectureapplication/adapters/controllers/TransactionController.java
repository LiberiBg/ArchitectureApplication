package esiea.yangnguyen.architectureapplication.adapters.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.exceptions.ErrorResponse;
import esiea.yangnguyen.architectureapplication.domain.exceptions.TransactionNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionInDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionOutDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.TransactionUpdateDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.TransactionMapper;
import esiea.yangnguyen.architectureapplication.usecase.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@Tag(name = "Transactions", description = "API de gestion des transactions d'échange")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Créer une transaction", description = "Initie une nouvelle transaction d'échange entre utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<TransactionOutDTO> createTransaction(@RequestBody TransactionInDTO transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionMapper.toDTO(createdTransaction));
    }

    @Operation(summary = "Récupérer toutes les transactions", description = "Retourne la liste de toutes les transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des transactions récupérée"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<TransactionOutDTO> getAllTransactions() {
        return transactionService.getAllTransactions().stream().map(TransactionMapper::toDTO).toList();
    }

    @Operation(summary = "Récupérer une transaction par ID", description = "Retourne les détails d'une transaction spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction trouvée"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transaction non trouvée",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionOutDTO> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id)
                .orElseThrow(TransactionNotFoundException::new);
        return ResponseEntity.ok(TransactionMapper.toDTO(transaction));
    }

    @Operation(summary = "Mettre à jour une transaction", description = "Modifie le statut ou les informations d'une transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Mise à jour non autorisée",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transaction non trouvée",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTransactionById(@PathVariable Long id, @RequestBody TransactionUpdateDTO transactionUpdateDTO) {
        transactionService.updateTransactionById(id, transactionUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Supprimer une transaction", description = "Supprime une transaction du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction supprimée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transaction non trouvée",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
        return ResponseEntity.noContent().build();
    }
}
