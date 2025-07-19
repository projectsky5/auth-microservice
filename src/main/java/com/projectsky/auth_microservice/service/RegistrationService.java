package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.*;

public interface RegistrationService {

    JwtAunthenticationDto completeRegistration(RegisterPasswordRequest request);

    void initRegistration(RegisterInitRequest request);

    void confirmCode(ConfirmCodeRequest request);
}
