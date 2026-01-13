package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void updateById(Long id, Product product);

    void deleteById(Long id);
}
