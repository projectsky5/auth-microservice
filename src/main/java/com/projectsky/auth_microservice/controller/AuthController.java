package com.projectsky.auth_microservice.controller;

import com.projectsky.auth_microservice.dto.*;
import com.projectsky.auth_microservice.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService service;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAunthenticationDto> singIn(
            @RequestBody @Valid SignInRequest request
    ) {
        return ResponseEntity.ok(service.signIn(request));
    }

}
