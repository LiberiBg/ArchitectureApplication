package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.exceptions.ItemCurrentlyInExchangeException;
import esiea.yangnguyen.architectureapplication.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProducCreatetDTO;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product postProduct(ProducCreatetDTO producCreatetDTO) {
        esiea.yangnguyen.architectureapplication.domain.service.ProductService.validateProductToBeCreated(producCreatetDTO);
        return productRepository.save(ProductMapper.toDomain(producCreatetDTO));
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        var product = getProductById(id);

        if (product.isEmpty()) throw new ItemNotFoundException("Product with id " + id + " not found");
        if (!esiea.yangnguyen.architectureapplication.domain.service.ProductService.isSafeToDelete(product.get()))
            throw new ItemCurrentlyInExchangeException("Product with id " + id + " is currently in exchange and cannot be deleted");

        productRepository.deleteById(id);
    }
}
