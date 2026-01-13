package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaUserEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper.JpaUserMapper;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public User save(User user) {
        JpaUserEntity jpaUserEntity = JpaUserMapper.toEntity(user);
        return JpaUserMapper.toDomain(springDataUserRepository.save(jpaUserEntity));
    }

    @Override
    public List<User> findAll() {
        return springDataUserRepository.findAll()
                .stream().map(JpaUserMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(long id) {
        return springDataUserRepository.findById(id).map(JpaUserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email).map(JpaUserMapper::toDomain);
    }

    @Override
    public void updateById(long id, User user) {
        JpaUserEntity jpaUserEntity = springDataUserRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (user.getFirstName() != null && !user.getFirstName().isBlank())
            jpaUserEntity.setFirstName(user.getFirstName());
        if (user.getLastName() != null && !user.getLastName().isBlank()) jpaUserEntity.setLastName(user.getLastName());
        if (user.getEmail() != null && !user.getEmail().isBlank()) jpaUserEntity.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isBlank()) jpaUserEntity.setPassword(user.getPassword());
        springDataUserRepository.save(jpaUserEntity);
    }

    @Override
    public void deleteById(Long id) {
        springDataUserRepository.deleteById(id);
    }
}
