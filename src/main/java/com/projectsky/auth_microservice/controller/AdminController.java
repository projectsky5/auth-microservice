package com.projectsky.auth_microservice.controller;

import com.projectsky.auth_microservice.dto.ChangeRoleRequest;
import com.projectsky.auth_microservice.dto.UserDto;
import com.projectsky.auth_microservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin");
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<UserDto> changeRole(
            @PathVariable Long id,
            @RequestBody ChangeRoleRequest request
    ) {
        return ResponseEntity.ok(userService.changeRole(request, id));
    }
}
