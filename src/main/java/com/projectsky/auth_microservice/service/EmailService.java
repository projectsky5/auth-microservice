package com.projectsky.auth_microservice.service;

public interface EmailService {

    void send(String to, String code);
}
