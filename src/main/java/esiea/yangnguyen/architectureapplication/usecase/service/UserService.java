package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        esiea.yangnguyen.architectureapplication.domain.service.UserService.validate(
                userCreateDTO.getFirstName(),
                userCreateDTO.getLastName(),
                userCreateDTO.getEmail(),
                userCreateDTO.getPassword()
        );return UserMapper.toDTO(userRepository.save(UserMapper.toDomain(userCreateDTO)));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDTO);
    }

    public void updateUserById(Long id, UserCreateDTO userCreateDTO) {
       userRepository.updateById(id, UserMapper.toDomain(userCreateDTO));
    }

    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.deleteById(id);
    }
}
