package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse getByUsername(String username);
}
