package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.AccountUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoAccountResponse;
import ru.kpfu.itis.beerokspring.exception.AccountNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.AccountMapper;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;
import ru.kpfu.itis.beerokspring.service.AccountService;
import ru.kpfu.itis.beerokspring.util.FileUploaderUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ru.kpfu.itis.beerokspring.util.Constants.EMAIL_REGEX;
import static ru.kpfu.itis.beerokspring.util.Constants.USERNAME_REGEX;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Value("${upload.url.suffix.avatars}")
    private String urlAvatars;

    private final AccountRepository repository;

    private final AccountMapper mapper;

    @Override
    public AccountResponse getByUsername(String username) {
        try {
            return mapper.toResponse(repository.findByUsername(username)
                    .orElseThrow(AccountNotFoundException::new));
        } catch (AccountNotFoundException e) {
            log.error("Account not found for username: {}", username, e);
            throw e;
        }
    }

    @Override
    public AccountEntity edit(String username, AccountUpdateRequest request) {
        AccountEntity account;
        try {
            account = repository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
            account.setUsername(request.username());
            account.setName(request.name());
            account.setLastname(request.lastname());
            account.setEmail(request.email());
            account.setAbout(request.about());
            if (!Objects.requireNonNull(request.avatar().getOriginalFilename()).isEmpty()) {
                String fileName = request.username() + UUID.randomUUID();
                account.setAvatar(FileUploaderUtil.uploadFile(request.avatar(), fileName, urlAvatars));
            }
            repository.save(account);
        } catch (AccountNotFoundException e) {
            log.error("Account not found for username: {}", username, e);
            throw e;
        }
        return account;
    }

    @Override
    public String validate(String username, AccountUpdateRequest request) {
        try {
            AccountEntity account = repository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
            String newUsername = request.username();
            String email = request.email();
            if ((repository.findByUsername(newUsername).isPresent() && !account.getUsername().equals(newUsername)) ||
                    (repository.findByEmail(email).isPresent() && !account.getEmail().equals(email))) {
                return "Пользователь с таким логином или почтой уже зарегистрирован";
            }

            if (!newUsername.matches(USERNAME_REGEX)) {
                return "Логин должен состоять из латинских букв";
            }

            if (!email.matches(EMAIL_REGEX)) {
                return "Неверно указана почта";
            }
            return null;
        } catch (AccountNotFoundException e) {
            log.error("Account not found for username: {}", username, e);
            throw e;
        }
    }
}
