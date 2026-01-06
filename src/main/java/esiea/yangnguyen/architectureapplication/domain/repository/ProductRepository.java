package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.usecase.dto.PostProductDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.ProductDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {
    Product save(Product product);
    Optional<ProductDTO> findById(Long id);
    List<ProductDTO> findAll();
    void deleteById(Long id);
    Product edit(PostProductDTO newProduct);
}
