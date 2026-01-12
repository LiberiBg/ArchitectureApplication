package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(long id);
    Optional<User> findByEmail(String email);
    void updateById(long id, User user);
    void deleteById(Long id);
}
