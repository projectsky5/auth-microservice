package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.JwtAunthenticationDto;
import com.projectsky.auth_microservice.dto.RefreshTokenRequest;
import com.projectsky.auth_microservice.exception.InvalidCredentialsException;
import com.projectsky.auth_microservice.exception.UserNotFoundException;
import com.projectsky.auth_microservice.model.RevokedToken;
import com.projectsky.auth_microservice.model.User;
import com.projectsky.auth_microservice.repository.RevokedTokenRepository;
import com.projectsky.auth_microservice.repository.UserRepository;
import com.projectsky.auth_microservice.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RevokedTokenRepository revokedTokenRepository;

    @Override
    public JwtAunthenticationDto refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if(revokedTokenRepository.existsByToken(refreshToken)) throw new InvalidCredentialsException("Token was revoked");

        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = userRepository.findByEmail(jwtService.getEmailFromToken(refreshToken))
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new InvalidCredentialsException("Invalid refresh token");
    }

    @Override
    public void revokeToken(String token) {
        if(token == null || !jwtService.validateJwtToken(token)) throw new InvalidCredentialsException("Invalid token");

        Date expiration = jwtService.getExpiration(token);

        RevokedToken revokedToken = RevokedToken.builder()
                .token(token)
                .expiresAt(expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();

        revokedTokenRepository.save(revokedToken);
    }
}
