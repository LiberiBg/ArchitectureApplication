package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaProductEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper.JpaProductMapper;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class JpaProductRepository implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;

    @Override
    public Product save(Product product) {
        JpaProductEntity jpaProductEntity = JpaProductMapper.toEntity(product);
        return JpaProductMapper.toDomain(springDataProductRepository.save(jpaProductEntity));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springDataProductRepository.findById(id)
                .map(JpaProductMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return springDataProductRepository.findAll()
                .stream().map(JpaProductMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        springDataProductRepository.deleteById(id);
    }
}
