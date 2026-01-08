package esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaUserEntity;
import esiea.yangnguyen.architectureapplication.domain.entities.User;

public class JpaUserMapper {
    public static User toDomain(JpaUserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastname(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }

    public static JpaUserEntity toEntity(User user) {
        return JpaUserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
