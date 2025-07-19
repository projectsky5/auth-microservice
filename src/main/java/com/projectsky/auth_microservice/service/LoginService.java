package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.JwtAunthenticationDto;
import com.projectsky.auth_microservice.dto.SignInRequest;

public interface LoginService {

    JwtAunthenticationDto signIn(SignInRequest request);
}
