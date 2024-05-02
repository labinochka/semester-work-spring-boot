package ru.kpfu.itis.beerokspring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;

import java.util.UUID;

public interface AuthService extends UserDetailsService {

    boolean validatePasswords(String passwordOne, String passwordTwo);

    UUID register(AccountRegistrationRequest request);

}
