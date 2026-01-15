package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository.EventPublisherRepository;
import esiea.yangnguyen.architectureapplication.domain.entities.CreatedProductEvent;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.exceptions.ItemCurrentlyInExchangeException;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.ProductMapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final EventPublisherRepository eventPublisherRepository;

    public ProductDTO postProduct(ProductCreateDTO productCreatedDTO) {
        esiea.yangnguyen.architectureapplication.domain.service.ProductService.validateProductToBeCreated(productCreatedDTO);

        final Product product = productRepository.save(ProductMapper.toDomain(productCreatedDTO));

        final CreatedProductEvent event = ProductMapper.toEvent(product);

        eventPublisherRepository.publish("products", String.valueOf(product.getId()), event);

        return ProductMapper.toDTO(product);
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(ProductMapper::toDTO);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toDTO).toList();
    }

    public void updateProductById(Long id, ProductCreateDTO productCreateDTO) {
        productRepository.updateById(id, ProductMapper.toDomain(productCreateDTO));
    }

    public void deleteProductById(Long id) {
        var product = getProductById(id);

        if (product.isEmpty()) throw new ItemNotFoundException("Product with id " + id + " not found");
        if (!esiea.yangnguyen.architectureapplication.domain.service.ProductService.isSafeToDelete(product.map(ProductMapper::toDomain).get()))
            throw new ItemCurrentlyInExchangeException("Product with id " + id + " is currently in exchange and cannot be deleted");

        productRepository.deleteById(id);
    }
}
