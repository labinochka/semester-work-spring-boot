package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.exception.TokenNotFoundException;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.VerificationTokenEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;
import ru.kpfu.itis.beerokspring.repository.VerificationTokenRepository;
import ru.kpfu.itis.beerokspring.service.VerificationTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository tokenRepository;

    private final AccountRepository accountRepository;

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(UUID uuid) {
        AccountEntity account = accountRepository.findById(uuid).get();
        VerificationTokenEntity token = new VerificationTokenEntity(account);
        tokenRepository.save(token);

        String subject = "Подтверждение почты";
        String verificationUrl = "http://localhost:8080/verify?token=" + token.getToken();
        String message = "Пожалуйста, нажмите для подтверждения почты:\n" + verificationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(account.getEmail());
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }

    @Override
    public VerificationTokenEntity getVerificationToken(String token) {
        try {
            return tokenRepository.findByToken(token).orElseThrow(TokenNotFoundException::new);
        } catch (TokenNotFoundException e) {
            log.error("Token {} not found", token, e);
            throw e;
        }
    }

    @Override
    public boolean verify(String token) {
        VerificationTokenEntity verificationToken = getVerificationToken(token);
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        AccountEntity account = verificationToken.getAccount();
        account.setVerified(true);
        accountRepository.save(account);
        delete(verificationToken);

        return true;
    }

    @Override
    public void delete(VerificationTokenEntity token) {
        getVerificationToken(token.getToken());
        tokenRepository.delete(token);
    }
}
