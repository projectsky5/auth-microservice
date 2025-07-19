package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.JwtAunthenticationDto;
import com.projectsky.auth_microservice.dto.RefreshTokenRequest;

public interface TokenService {

    JwtAunthenticationDto refreshToken(RefreshTokenRequest request);

    void revokeToken(String token);
}
