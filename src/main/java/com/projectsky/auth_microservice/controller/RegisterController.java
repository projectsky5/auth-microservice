package com.projectsky.auth_microservice.controller;

import com.projectsky.auth_microservice.dto.ConfirmCodeRequest;
import com.projectsky.auth_microservice.dto.JwtAunthenticationDto;
import com.projectsky.auth_microservice.dto.RegisterInitRequest;
import com.projectsky.auth_microservice.dto.RegisterPasswordRequest;
import com.projectsky.auth_microservice.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegistrationService service;

    @PostMapping("/init")
    public ResponseEntity<Void> registerInit(
            @RequestBody @Valid RegisterInitRequest request
    ) {
        service.initRegistration(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> registerConfirm(
            @RequestBody @Valid ConfirmCodeRequest request
    ) {
        service.confirmCode(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete")
    public ResponseEntity<JwtAunthenticationDto> registerComplete(
            @RequestBody @Valid RegisterPasswordRequest request
    ) {
        return ResponseEntity.ok(service.completeRegistration(request));
    }
}
