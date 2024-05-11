package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.request.AccountUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoAccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse getByUsername(String username);

    List<ShortInfoAccountResponse> getByRoleId(int roleId);

    void edit(String username, AccountUpdateRequest request);

    String validate(String username, AccountUpdateRequest request);
}
