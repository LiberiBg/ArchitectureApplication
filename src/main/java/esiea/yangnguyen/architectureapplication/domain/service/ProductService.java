package esiea.yangnguyen.architectureapplication.domain.service;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.domain.entities.ProductStatus;
import esiea.yangnguyen.architectureapplication.usecase.dto.PostProductDTO;
import lombok.experimental.UtilityClass;

import static esiea.yangnguyen.architectureapplication.utils.Utils.isNullOrEmpty;

@UtilityClass
public class ProductService {
    public static void validateProductToBeCreated(PostProductDTO product) {
        if (isNullOrEmpty(product.name())) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (isNullOrEmpty(product.description())) {
            throw new IllegalArgumentException("Product description cannot be null or empty");
        }
        if (isNullOrEmpty(product.brand())) {
            throw new IllegalArgumentException("Product brand cannot be null or empty");
        }
        if (product.state() == null) {
            throw new IllegalArgumentException("Product state cannot be null");
        }
        if (isNullOrEmpty(product.size())) {
            throw new IllegalArgumentException("Product size cannot be null or empty");
        }
        if (isNullOrEmpty(product.category())) {
            throw new IllegalArgumentException("Product category cannot be null or empty");
        }
        if (isNullOrEmpty(product.season())) {
            throw new IllegalArgumentException("Product season cannot be null or empty");
        }
        if (product.providerId() <= 0) {
            throw new IllegalArgumentException("Provider ID must be a positive number");
        }
        if (product.status() == null) {
            throw new IllegalArgumentException("Product status cannot be null");
        }
    }

    public static boolean isSafeToEdit(Product product) {
        return !product.getStatus().equals(ProductStatus.PENDING);
    }

    public static boolean isSafeToDelete(Product product) {
        return !product.getStatus().equals(ProductStatus.PENDING);
    }
}
