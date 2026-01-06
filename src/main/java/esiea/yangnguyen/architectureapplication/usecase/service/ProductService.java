package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import esiea.yangnguyen.architectureapplication.usecase.dto.PostProductDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductDTO;
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

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Iterable<ProductDTO> getAllProducts() {
        return productRepository.findAll();
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
