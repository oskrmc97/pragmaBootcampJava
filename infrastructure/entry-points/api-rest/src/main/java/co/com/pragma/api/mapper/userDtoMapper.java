package co.com.pragma.api.mapper;

import co.com.pragma.api.userDto.UserIntDto;
import co.com.pragma.api.userDto.userOutDto;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface userDtoMapper {
    userOutDto toDto(User user);
    UserIntDto toIntDto(User user);
    List<userOutDto> toDtoList(List<User> users);
    User toUserIntDto(UserIntDto userDto);
}
