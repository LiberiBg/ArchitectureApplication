package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.entities.ProductStatus;
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
        return productRepository.save(ProductMapper.toDomain(postProductDTO, calculateScore(postProductDTO)));
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        var product = getProductById(id);

        if (product.isEmpty())
            throw new ItemNotFoundException("Product with id " + id + " not found");
        if (product.get().getStatus().equals(ProductStatus.PENDING))
            throw new ItemCurrentlyInExchangeException("Product with id " + id + " is currently in exchange and cannot be deleted");

        productRepository.deleteById(id);
    }

    public void editProductById(PostProductDTO postProductDTO) {
        var productToEdit = getProductById(postProductDTO.getId());

        if (productToEdit.isEmpty())
            throw new ItemNotFoundException("Product with id " + postProductDTO.getId() + " not found");
        if (productToEdit.get().getStatus().equals(ProductStatus.PENDING))
            throw new ItemCurrentlyInExchangeException("Product with id " + postProductDTO.getId() + " is currently in exchange and cannot be edited");

        productRepository.edit(postProductDTO);
    }

    private int calculateScore(PostProductDTO postProductDTO) {
        int score = 0;

        if (postProductDTO.getState() != null) {
            switch (postProductDTO.getState()) {
                case NEW:
                    score += 5;
                    break;
                case LIKE_NEW:
                    score += 4;
                    break;
                case GOOD:
                    score += 3;
                    break;
                case ACCEPTABLE:
                    score += 2;
                    break;
                case POOR:
                    score += 1;
                    break;
            }
        }
    return score;
    }

}
