package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.VerificationTokenEntity;

public interface VerificationTokenService {

    void sendEmail(AccountEntity account);

    VerificationTokenEntity getVerificationToken(String token);

    void delete(VerificationTokenEntity token);
}
