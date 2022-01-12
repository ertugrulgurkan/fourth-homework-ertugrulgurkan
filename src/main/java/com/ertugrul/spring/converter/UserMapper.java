package com.ertugrul.spring.converter;

import com.ertugrul.spring.dto.UserDto;
import com.ertugrul.spring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto convertUserDtoToUser(User user);

    User convertUserDtoToUser(UserDto userDto);

    List<UserDto> convertAllUserDtoToUser(List<User> userList);

    List<User> convertAllUserToUserDto(List<UserDto> userDtoList);
}
