package com.example.webstore.service;

import com.example.webstore.dto.user.RegisterUserDto;
import com.example.webstore.dto.user.RegisterUserResponseDto;
import com.example.webstore.exception.UserRegistrationException;
import com.example.webstore.mapper.UserMapper;
import com.example.webstore.model.Role;
import com.example.webstore.model.User;
import com.example.webstore.repository.RoleRepository;
import com.example.webstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public RegisterUserResponseDto register(RegisterUserDto userDto) throws UserRegistrationException {
        if (userRepository.findUserByEmail(userDto.getEmail()).isPresent()) {
            throw new UserRegistrationException("Can't register user");
        }
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleRepository.getRoleByName(Role.RoleName.USER));
        user.setCreatedAt(LocalDateTime.now());
        return userMapper.toResponseDto(userRepository.save(user));
    }

    public User findUserFromAuthentication(Authentication authentication) {
        return userRepository.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Can't find user"));
    }
}
