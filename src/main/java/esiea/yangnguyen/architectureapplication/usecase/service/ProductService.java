package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.exceptions.ItemCurrentlyInExchangeException;
import esiea.yangnguyen.architectureapplication.domain.repository.EventPublisherRepository;
import esiea.yangnguyen.architectureapplication.domain.entities.CreatedProductEvent;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.domain.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductInDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.ProductMapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final EventPublisherRepository eventPublisherRepository;

    public Product postProduct(ProductInDTO productCreatedDTO) {
        esiea.yangnguyen.architectureapplication.domain.service.ProductService.validateProductToBeCreated(productCreatedDTO);

        final Product product = productRepository.save(ProductMapper.toDomain(productCreatedDTO));

        final CreatedProductEvent event = ProductMapper.toEvent(product);

        eventPublisherRepository.publish("products", String.valueOf(product.getId()), event);

        return product;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().toList();
    }

    public void updateProductById(Long id, ProductInDTO productInDTO) {
        if (!esiea.yangnguyen.architectureapplication.domain.service.ProductService.isSafeToEdit(getProductById(id).orElseThrow(() ->
                new ItemNotFoundException("Product with id " + id + " not found")))) {
            throw new ItemCurrentlyInExchangeException("Product with id " + id + " is currently in exchange and cannot be edited");
        }
        productRepository.updateById(id, ProductMapper.toDomain(productInDTO));
    }

    public void deleteProductById(Long id) {
        var product = getProductById(id);

        if (product.isEmpty()) throw new ItemNotFoundException("Product with id " + id + " not found");
        if (!esiea.yangnguyen.architectureapplication.domain.service.ProductService.isSafeToDelete(product.get()))
            throw new ItemCurrentlyInExchangeException("Product with id " + id + " is currently in exchange and cannot be deleted");

        productRepository.deleteById(id);
    }
}
