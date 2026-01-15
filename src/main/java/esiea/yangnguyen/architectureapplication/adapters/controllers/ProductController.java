package esiea.yangnguyen.architectureapplication.adapters.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.exceptions.ErrorResponse;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductInDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductOutDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.ProductMapper;
import esiea.yangnguyen.architectureapplication.usecase.service.ProductService;
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
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Products", description = "API de gestion des produits")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Créer un produit", description = "Ajoute un nouveau produit au catalogue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produit créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ProductOutDTO> createProduct(@RequestBody ProductInDTO product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDTO(productService.postProduct(product)));
    }

    @Operation(summary = "Récupérer tous les produits", description = "Retourne la liste de tous les produits disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des produits récupérée"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<ProductOutDTO> getAllProducts() {
        return productService.getAllProducts().stream().map(ProductMapper::toDTO).toList();
    }

    @Operation(summary = "Récupérer un produit par ID", description = "Retourne les détails d'un produit spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit trouvé"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductOutDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ItemNotFoundException("Product with id " + id + " not found"));
        return ResponseEntity.ok(ProductMapper.toDTO(product));
    }


    @Operation(summary = "Mettre à jour un produit", description = "Modifie les informations d'un produit existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produit mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProductById(@PathVariable Long id, @RequestBody ProductInDTO productInDTO) {
        productService.updateProductById(id, productInDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Supprimer un produit", description = "Supprime un produit du catalogue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produit supprimé avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Produit actuellement en échange",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
