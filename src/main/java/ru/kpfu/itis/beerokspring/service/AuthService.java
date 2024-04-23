package ru.kpfu.itis.beerokspring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;

public interface AuthService extends UserDetailsService {

    String register(AccountRegistrationRequest request);
}
