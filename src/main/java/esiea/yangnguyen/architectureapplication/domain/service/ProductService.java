package esiea.yangnguyen.architectureapplication.domain.service;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.domain.entities.ProductStatus;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductInDTO;
import lombok.experimental.UtilityClass;

import static esiea.yangnguyen.architectureapplication.utils.Utils.isNullOrEmpty;

@UtilityClass
public class ProductService {
    public static void validateProductToBeCreated(ProductInDTO product) {
        if (isNullOrEmpty(product.getName())) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (isNullOrEmpty(product.getDescription())) {
            throw new IllegalArgumentException("Product description cannot be null or empty");
        }
        if (isNullOrEmpty(product.getBrand())) {
            throw new IllegalArgumentException("Product brand cannot be null or empty");
        }
        if (product.getState() == null) {
            throw new IllegalArgumentException("Product state cannot be null");
        }
        if (isNullOrEmpty(product.getSize())) {
            throw new IllegalArgumentException("Product size cannot be null or empty");
        }
        if (isNullOrEmpty(product.getCategory())) {
            throw new IllegalArgumentException("Product category cannot be null or empty");
        }
        if (isNullOrEmpty(product.getSeason())) {
            throw new IllegalArgumentException("Product season cannot be null or empty");
        }
        if (product.getProviderId() <= 0) {
            throw new IllegalArgumentException("Provider ID must be a positive number");
        }
        if (product.getStatus() == null) {
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
