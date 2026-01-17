package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserInDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.UserOutDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User toDomain(UserInDTO userInDTO) {
        return new User(userInDTO.getFirstName(), userInDTO.getLastName(), userInDTO.getEmail(), userInDTO.getPassword());
    }

    public static UserOutDTO toDTO(User user) {
        return new UserOutDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
