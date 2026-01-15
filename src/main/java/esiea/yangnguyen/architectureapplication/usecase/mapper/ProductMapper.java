package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.CreatedProductEvent;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductInDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductOutDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public static Product toDomain(ProductInDTO productDTO) {
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

    private int calculateScore(ProductInDTO producCreatetDTO) {
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

    public static ProductOutDTO toDTO(Product product) {
        return new ProductOutDTO(
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

    public static Product toDomain(ProductOutDTO productOutDTO) {
        Product product = new Product(
                productOutDTO.getName(),
                productOutDTO.getDescription(),
                productOutDTO.getBrand(),
                productOutDTO.getState(),
                productOutDTO.getSize(),
                productOutDTO.getCategory(),
                productOutDTO.getSeason(),
                productOutDTO.getScore(),
                productOutDTO.getProviderId(),
                productOutDTO.getStatus()
        );
        if (productOutDTO.getId() != 0) {
            product.setId(productOutDTO.getId());
        }
        return product;
    }
}
