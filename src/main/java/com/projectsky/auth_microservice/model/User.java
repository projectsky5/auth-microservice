package com.projectsky.auth_microservice.model;

import com.projectsky.auth_microservice.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 16)
    private String login;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(unique = true, nullable = false, length = 16)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private Role role;

    @Column(length = 16)
    private String confirmationCode;

    @Column(length = 4)
    private boolean isConfirmed;
}
