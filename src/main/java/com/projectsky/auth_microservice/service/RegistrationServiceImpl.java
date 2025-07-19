package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.*;
import com.projectsky.auth_microservice.enums.Role;
import com.projectsky.auth_microservice.exception.ConfirmationFailedException;
import com.projectsky.auth_microservice.exception.InvalidCredentialsException;
import com.projectsky.auth_microservice.exception.UserAlreadyExistsException;
import com.projectsky.auth_microservice.exception.UserNotFoundException;
import com.projectsky.auth_microservice.model.User;
import com.projectsky.auth_microservice.repository.UserRepository;
import com.projectsky.auth_microservice.security.jwt.JwtService;
import com.projectsky.auth_microservice.util.ConfirmationCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

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


    @Override
    public JwtAunthenticationDto refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = userRepository.findByEmail(jwtService.getEmailFromToken(refreshToken))
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new InvalidCredentialsException("Invalid refresh token");
    }

    @Override
    public JwtAunthenticationDto completeRegistration(RegisterPasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!user.isConfirmed()) throw new ConfirmationFailedException("User is not confirmed");

        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.GUEST);
        userRepository.save(user);

        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public void initRegistration(RegisterInitRequest request) {
        if(userRepository.existsByEmail(request.email())) throw new UserAlreadyExistsException("User already exists");

        String code = ConfirmationCodeUtil.generateConfirmationCode();

        User user = User.builder()
                .email(request.email())
                .login(request.login())
                .isConfirmed(false)
                .confirmationCode(code)
                .build();

        userRepository.save(user);
        emailService.send(user.getEmail(), code);
    }

    @Override
    public void confirmCode(ConfirmCodeRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.isConfirmed()) {
            throw new ConfirmationFailedException("User already confirmed");
        }

        if (!request.code().equals(user.getConfirmationCode())) {
            throw new ConfirmationFailedException("Incorrect confirmation code");
        }

        user.setConfirmed(true);
        user.setConfirmationCode("");
        userRepository.save(user);
    }
}
