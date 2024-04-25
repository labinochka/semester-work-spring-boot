package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.VerificationTokenEntity;

import java.util.UUID;

public interface VerificationTokenService {

    void sendEmail(UUID uuid);

    VerificationTokenEntity getVerificationToken(String token);

    boolean verify(String token);

    void delete(VerificationTokenEntity token);
}
