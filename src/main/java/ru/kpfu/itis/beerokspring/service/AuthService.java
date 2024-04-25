package ru.kpfu.itis.beerokspring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;

import java.util.UUID;

public interface AuthService extends UserDetailsService {

    String validate(AccountRegistrationRequest request);

    UUID register(AccountRegistrationRequest request);

}
