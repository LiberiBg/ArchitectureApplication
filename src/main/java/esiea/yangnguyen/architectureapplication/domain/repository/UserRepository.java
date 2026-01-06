package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(long id);
}
