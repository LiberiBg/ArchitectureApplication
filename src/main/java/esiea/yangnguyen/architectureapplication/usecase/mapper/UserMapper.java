package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User toDomain(UserCreateDTO userCreateDTO) {
        return new User(userCreateDTO.getFirstName(), userCreateDTO.getLastName(), userCreateDTO.getEmail(), userCreateDTO.getPassword());
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastname(), user.getEmail());
    }
}
