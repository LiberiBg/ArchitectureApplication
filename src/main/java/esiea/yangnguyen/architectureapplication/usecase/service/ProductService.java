package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.exceptions.ItemCurrentlyInExchangeException;
import esiea.yangnguyen.architectureapplication.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.PostProductDTO;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.mapper.ProductMapper;

import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product postProduct(PostProductDTO postProductDTO) {
        esiea.yangnguyen.architectureapplication.domain.service.ProductService.validateProductToBeCreated(postProductDTO);
        return productRepository.save(ProductMapper.toDomain(postProductDTO));
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
        if (esiea.yangnguyen.architectureapplication.domain.service.ProductService.isSafeToDelete(product.get()))
            throw new ItemCurrentlyInExchangeException("Product with id " + id + " is currently in exchange and cannot be deleted");

        productRepository.deleteById(id);
    }

    public void editProductById(PostProductDTO postProductDTO) {
        var productToEdit = getProductById(postProductDTO.id());

        if (productToEdit.isEmpty())
            throw new ItemNotFoundException("Product with id " + postProductDTO.id() + " not found");
        if (esiea.yangnguyen.architectureapplication.domain.service.ProductService.isSafeToEdit(productToEdit.get()))
            throw new ItemCurrentlyInExchangeException("Product with id " + postProductDTO.id() + " is currently in exchange and cannot be edited");

        productRepository.edit(postProductDTO);
    }
}
