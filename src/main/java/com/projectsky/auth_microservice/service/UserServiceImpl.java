package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.ChangeRoleRequest;
import com.projectsky.auth_microservice.dto.UserDto;
import com.projectsky.auth_microservice.enums.Role;
import com.projectsky.auth_microservice.exception.InvalidRoleException;
import com.projectsky.auth_microservice.exception.UserNotFoundException;
import com.projectsky.auth_microservice.model.User;
import com.projectsky.auth_microservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto changeRole(ChangeRoleRequest request, Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        try{
            user.setRole(Role.valueOf(request.role().toUpperCase()));
        } catch (IllegalArgumentException e){
            throw new InvalidRoleException("Unknown role: " + request.role());
        }

        return mapToDto(user);
    }

    private UserDto mapToDto(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
