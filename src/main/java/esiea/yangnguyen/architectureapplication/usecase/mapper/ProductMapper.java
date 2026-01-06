package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.dto.PostProductDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public static Product toDomain(PostProductDTO productDTO, int score) {
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getBrand(),
                productDTO.getState(),
                productDTO.getSize(),
                productDTO.getCategory(),
                productDTO.getSeason(),
                score,
                productDTO.getProviderId(),
                productDTO.getStatus()
        );
    }
}
