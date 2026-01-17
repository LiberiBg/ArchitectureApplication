package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserInDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.UserMapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserInDTO userInDTO) {
        esiea.yangnguyen.architectureapplication.domain.service.UserService.validate(
                userInDTO.getFirstName(),
                userInDTO.getLastName(),
                userInDTO.getEmail(),
                userInDTO.getPassword()
        );return userRepository.save(UserMapper.toDomain(userInDTO));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUserById(Long id, UserInDTO userInDTO) {
       userRepository.updateById(id, UserMapper.toDomain(userInDTO));
    }

    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.deleteById(id);
    }
}
