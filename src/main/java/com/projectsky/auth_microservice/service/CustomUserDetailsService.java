package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.ChangeRoleRequest;
import com.projectsky.auth_microservice.dto.UserDto;
import com.projectsky.auth_microservice.exception.UserNotFoundException;
import com.projectsky.auth_microservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
