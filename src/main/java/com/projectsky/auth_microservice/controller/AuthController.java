package com.projectsky.auth_microservice.controller;

import com.projectsky.auth_microservice.dto.*;
import com.projectsky.auth_microservice.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService service;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAunthenticationDto> singIn(
            @RequestBody @Valid SignInRequest request
    ) {
        return ResponseEntity.ok(service.signIn(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAunthenticationDto> refresh(
            @RequestBody @Valid RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

    @PostMapping("/register-init")
    public ResponseEntity<Void> registerInit(
            @RequestBody @Valid RegisterInitRequest request
    ) {
        service.initRegistration(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register-confirm")
    public ResponseEntity<Void> registerConfirm(
            @RequestBody @Valid ConfirmCodeRequest request
    ) {
        service.confirmCode(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register-complete")
    public ResponseEntity<JwtAunthenticationDto> registerComplete(
            @RequestBody @Valid RegisterPasswordRequest request
    ) {
        return ResponseEntity.ok(service.completeRegistration(request));
    }

}
