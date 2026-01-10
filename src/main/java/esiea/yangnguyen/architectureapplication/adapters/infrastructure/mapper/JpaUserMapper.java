package esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaUserEntity;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JpaUserMapper {
    public static User toDomain(JpaUserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }

    public static JpaUserEntity toEntity(User user) {
        JpaUserEntity.JpaUserEntityBuilder jpaUserEntityBuilder = JpaUserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword());
        if (user.getId() != 0)
            jpaUserEntityBuilder.id(user.getId());
        return jpaUserEntityBuilder.build();
    }
}
