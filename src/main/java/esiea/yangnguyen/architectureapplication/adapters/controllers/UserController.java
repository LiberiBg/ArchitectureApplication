package esiea.yangnguyen.architectureapplication.adapters.controllers;

import esiea.yangnguyen.architectureapplication.exceptions.UserNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserAuthDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserDTO;
import esiea.yangnguyen.architectureapplication.usecase.service.UserService;
import esiea.yangnguyen.architectureapplication.utils.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserAuthDTO userAuthDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthDTO.getEmail(),
                        userAuthDTO.getPassword()
                )
        );
        UserDTO userDTO = userService.getUserByEmail(userAuthDTO.getEmail()).orElseThrow(UserNotFoundException::new);
        String token = jwtService.generateToken(userDTO.getId());
        return Map.of("token", token);
    }

    @PostMapping("/signup")
    public UserDTO createUser(@RequestBody UserCreateDTO user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public void updateUserById(@PathVariable Long id, @RequestBody UserCreateDTO user) {
        userService.updateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
