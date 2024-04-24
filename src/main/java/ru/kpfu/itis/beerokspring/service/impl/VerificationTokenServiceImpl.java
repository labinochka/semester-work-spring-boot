package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.exception.TokenNotFoundException;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.VerificationTokenEntity;
import ru.kpfu.itis.beerokspring.repository.VerificationTokenRepository;
import ru.kpfu.itis.beerokspring.service.VerificationTokenService;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository repository;

    @Override
    public void sendEmail(AccountEntity account) {

    }

    @Override
    public VerificationTokenEntity getVerificationToken(String token) {
        try {
            return repository.findByToken(token).orElseThrow(TokenNotFoundException::new);
        } catch (TokenNotFoundException e) {
            log.error("Token {} not found", token, e);
            throw e;
        }
    }

    @Override
    public void delete(VerificationTokenEntity token) {
        getVerificationToken(token.getToken());
        repository.delete(token);
    }
}
