package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.request.AccountUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;

import java.util.UUID;

public interface AccountService {

    AccountResponse getByUsername(String username);

    void edit(String username, AccountUpdateRequest request);

    String validate(String username, AccountUpdateRequest request);
}
