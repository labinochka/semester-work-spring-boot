package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoAccountResponse;
import ru.kpfu.itis.beerokspring.exception.AccountNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.AccountMapper;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.RoleEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;
import ru.kpfu.itis.beerokspring.service.AdminService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AccountRepository repository;

    private final AccountMapper mapper;

    @Override
    public void addAdmin(String username) {
        AccountEntity account = repository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        account.setRole(new RoleEntity(1, "ROLE_ADMIN"));
        repository.save(account);
    }

    @Override
    public List<ShortInfoAccountResponse> getAdmins() {
        return mapper.toShortResponse(repository.findByRoleName("ROLE_ADMIN"));
    }

    @Override
    public void deleteAdmin(String username) {
        AccountEntity account = repository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        account.setRole(new RoleEntity(2, "ROLE_USER"));
        repository.save(account);
    }
}
