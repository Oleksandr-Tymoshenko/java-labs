package com.example.webstore.mapper;

import com.example.webstore.dto.user.RegisterUserDto;
import com.example.webstore.dto.user.RegisterUserResponseDto;
import com.example.webstore.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {
    User toUser(RegisterUserDto userDto);

    RegisterUserResponseDto toResponseDto(User user);
}
