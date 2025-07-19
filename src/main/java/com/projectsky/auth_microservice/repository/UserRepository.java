package com.projectsky.auth_microservice.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.projectsky.auth_microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
