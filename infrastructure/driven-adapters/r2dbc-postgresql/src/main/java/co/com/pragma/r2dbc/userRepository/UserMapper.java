package co.com.pragma.r2dbc.userRepository;

import co.com.pragma.model.user.RolUser;
import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.RolEntity;
import co.com.pragma.r2dbc.entity.userEntity;

import java.math.BigInteger;

public class UserMapper {
    public static User toDomain(userEntity userEntity, RolEntity rolEntity) {
        RolUser rolUser = RolUser.builder()
                .idRol(rolEntity.getId_rol())
                .name(rolEntity.getName())
                .description(rolEntity.getDescription())
                .build();

        return User.builder()
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .birthdate(userEntity.getBirthdate())
                .address(userEntity.getAddress())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .document(userEntity.getDocument())
                .salary(userEntity.getSalary())
                .rolUser(rolUser.getName())
                .build();
    }
}
