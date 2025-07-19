package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.JwtAunthenticationDto;
import com.projectsky.auth_microservice.dto.SignInRequest;
import com.projectsky.auth_microservice.exception.ConfirmationFailedException;
import com.projectsky.auth_microservice.exception.InvalidCredentialsException;
import com.projectsky.auth_microservice.exception.UserNotFoundException;
import com.projectsky.auth_microservice.model.User;
import com.projectsky.auth_microservice.repository.UserRepository;
import com.projectsky.auth_microservice.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtAunthenticationDto signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!user.isConfirmed()) throw new ConfirmationFailedException("User is not confirmed");

        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            return jwtService.generateAuthToken(user.getEmail());
        }
        throw new InvalidCredentialsException("Email or password is incorrect");
    }
}
