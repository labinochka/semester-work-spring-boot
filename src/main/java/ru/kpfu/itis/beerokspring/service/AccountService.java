package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;
import ru.kpfu.itis.beerokspring.dto.request.AccountUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoAccountResponse;
import ru.kpfu.itis.beerokspring.model.AccountEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponse getByUsername(String username);

    AccountEntity edit(String username, AccountUpdateRequest request);

    String validate(String username, AccountUpdateRequest request);

    boolean validatePasswords(String passwordOne, String passwordTwo);

    UUID register(AccountRegistrationRequest request);
}
