package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.exception.AccountNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.AccountMapper;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;
import ru.kpfu.itis.beerokspring.service.AccountService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

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
}
