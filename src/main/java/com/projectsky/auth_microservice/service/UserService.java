package com.projectsky.auth_microservice.service;

import com.projectsky.auth_microservice.dto.ChangeRoleRequest;
import com.projectsky.auth_microservice.dto.UserDto;

public interface UserService {

    public UserDto changeRole(ChangeRoleRequest request, Long id);
}
