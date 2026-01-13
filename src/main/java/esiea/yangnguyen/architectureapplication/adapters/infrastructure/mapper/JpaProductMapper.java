package esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaProductEntity;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JpaProductMapper {

    public static Product toDomain(esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaProductEntity jpaProduct) {
        if (jpaProduct == null) {
            return null;
        }
        return new Product(
                jpaProduct.getId(),
                jpaProduct.getName(),
                jpaProduct.getDescription(),
                jpaProduct.getBrand(),
                jpaProduct.getState(),
                jpaProduct.getSize(),
                jpaProduct.getCategory(),
                jpaProduct.getSeason(),
                jpaProduct.getScore(),
                jpaProduct.getProviderId(),
                jpaProduct.getStatus()
        );
    }

    public static JpaProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }
        JpaProductEntity.JpaProductEntityBuilder jpaProductEntityBuilder = JpaProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .state(product.getState())
                .size(product.getSize())
                .category(product.getCategory())
                .season(product.getSeason())
                .score(product.getScore())
                .providerId(product.getProviderId())
                .status(product.getStatus());
        if (product.getId() != 0)
            jpaProductEntityBuilder.id(product.getId());
        return jpaProductEntityBuilder.build();
    }
}
