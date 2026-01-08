package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaUserEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper.JpaUserMapper;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Optional<User> findById(long id) {
        return springDataUserRepository.findById(id).map(JpaUserMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springDataUserRepository.deleteById(id);
    }
}
