package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductCreateDTO;
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
}
