package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.dto.PostProductDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public static Product toDomain(PostProductDTO productDTO) {
        return new Product(
                productDTO.id(),
                productDTO.name(),
                productDTO.description(),
                productDTO.brand(),
                productDTO.state(),
                productDTO.size(),
                productDTO.category(),
                productDTO.season(),
                calculateScore(productDTO),
                productDTO.providerId(),
                productDTO.status()
        );
    }

    private int calculateScore(PostProductDTO postProductDTO) {
        int score = 0;

        if (postProductDTO.state() != null) {
            switch (postProductDTO.state()) {
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
