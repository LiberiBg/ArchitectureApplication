package esiea.yangnguyen.architectureapplication.adapters.controllers;

import esiea.yangnguyen.architectureapplication.exceptions.ErrorResponse;
import esiea.yangnguyen.architectureapplication.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageOutDTO;
import esiea.yangnguyen.architectureapplication.usecase.service.MessageService;
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

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
@Tag(name = "Messages", description = "API de gestion des messages entre utilisateurs")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Envoyer un message", description = "Crée et envoie un nouveau message entre utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message envoyé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MessageOutDTO> sendMessage(@RequestBody MessageCreateDTO messageCreateDTO) {
        MessageOutDTO message = messageService.sendMessage(messageCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @Operation(summary = "Récupérer tous les messages", description = "Retourne la liste de tous les messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des messages récupérée"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public Iterable<MessageOutDTO> getAllMessages() {
        return messageService.findAll();
    }

    @Operation(summary = "Récupérer un message par ID", description = "Retourne les détails d'un message spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message trouvé"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Message non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MessageOutDTO> getMessage(@PathVariable Long id) {
        MessageOutDTO message = messageService.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Message with id " + id + " not found"));
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Supprimer un message", description = "Supprime un message du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Message supprimé avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Message non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        messageService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

}
