package com.projectsky.auth_microservice.controller;

import com.projectsky.auth_microservice.dto.JwtAunthenticationDto;
import com.projectsky.auth_microservice.dto.RefreshTokenRequest;
import com.projectsky.auth_microservice.dto.RevokeTokenRequest;
import com.projectsky.auth_microservice.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService service;

    @PostMapping("/refresh")
    public ResponseEntity<JwtAunthenticationDto> refresh(
            @RequestBody @Valid RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(
            @RequestBody @Valid RevokeTokenRequest request
    ) {
        service.revokeToken(request.token());
        return ResponseEntity.ok().build();
    }
}
