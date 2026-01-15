package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.CreatedProductEvent;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public static Product toDomain(ProductCreateDTO productDTO) {
        return new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getBrand(),
                productDTO.getState(),
                productDTO.getSize(),
                productDTO.getCategory(),
                productDTO.getSeason(),
                calculateScore(productDTO),
                productDTO.getProviderId(),
                productDTO.getStatus()
        );
    }

    public static CreatedProductEvent toEvent(Product product) {
        return new CreatedProductEvent(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getState(),
                product.getSize(),
                product.getCategory(),
                product.getSeason(),
                product.getProviderId(),
                product.getStatus()
        );
    }

    private int calculateScore(ProductCreateDTO producCreatetDTO) {
        int score = 0;

        if (producCreatetDTO.getState() != null) {
            switch (producCreatetDTO.getState()) {
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

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getState(),
                product.getSize(),
                product.getCategory(),
                product.getSeason(),
                product.getScore(),
                product.getProviderId(),
                product.getStatus()
        );
    }

    public static Product toDomain(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getBrand(),
                productDTO.getState(),
                productDTO.getSize(),
                productDTO.getCategory(),
                productDTO.getSeason(),
                productDTO.getScore(),
                productDTO.getProviderId(),
                productDTO.getStatus()
        );
        if (productDTO.getId() != 0) {
            product.setId(productDTO.getId());
        }
        return product;
    }
}
