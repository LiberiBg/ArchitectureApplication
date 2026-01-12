package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaProductEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper.JpaProductMapper;
import esiea.yangnguyen.architectureapplication.domain.entities.Product;
import esiea.yangnguyen.architectureapplication.domain.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public void updateById(Long id, Product product) {
        JpaProductEntity jpaProductEntity = springDataProductRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        jpaProductEntity.setName(product.getName());
        jpaProductEntity.setDescription(product.getDescription());
        jpaProductEntity.setBrand(product.getBrand());
        jpaProductEntity.setState(product.getState());
        jpaProductEntity.setSize(product.getSize());
        jpaProductEntity.setCategory(product.getCategory());
        jpaProductEntity.setSeason(product.getSeason());
        jpaProductEntity.setScore(jpaProductEntity.getScore());
        jpaProductEntity.setProviderId(jpaProductEntity.getProviderId());
        jpaProductEntity.setStatus(product.getStatus());

        springDataProductRepository.save(jpaProductEntity);
    }

    @Override
    public void deleteById(Long id) {
        springDataProductRepository.deleteById(id);
    }
}
