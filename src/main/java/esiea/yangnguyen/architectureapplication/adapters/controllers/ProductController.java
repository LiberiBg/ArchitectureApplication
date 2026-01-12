package esiea.yangnguyen.architectureapplication.adapters.controllers;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody ProductCreateDTO product) {
        return productService.postProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElse(null);
    }


    @PutMapping("/{id}")
    public void updateProductById(@PathVariable Long id, @RequestBody ProductCreateDTO productCreateDTO) {
        productService.updateProductById(id, productCreateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
