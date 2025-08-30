package co.com.pragma.api.mapper;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.UserIntDto;
import co.com.pragma.model.user.dto.userOutDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    userOutDto toDto(User user);
    UserIntDto toIntDto(User user);
    List<userOutDto> toDtoList(List<User> users);
    User toUserFromIntDto(UserIntDto userDto);
}
