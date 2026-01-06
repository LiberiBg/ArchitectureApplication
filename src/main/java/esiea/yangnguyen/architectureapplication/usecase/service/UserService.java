package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.UserMapper;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreateDTO userCreateDTO) {
        return userRepository.save(UserMapper.toDomain(userCreateDTO));
    }

    public Optional<UserDTO> getUserById(long id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }
}
