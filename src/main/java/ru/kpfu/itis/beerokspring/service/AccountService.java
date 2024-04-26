package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.request.AccountUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;

import java.util.UUID;

public interface AccountService {

    AccountResponse getByUsername(String username);

    void edit(UUID uuid, AccountUpdateRequest account);

    String validate(UUID id, AccountUpdateRequest account);
}
