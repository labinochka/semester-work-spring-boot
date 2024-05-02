package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;
import ru.kpfu.itis.beerokspring.mapper.AccountMapper;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.RoleEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.AuthService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository repository;

    private final AccountMapper mapper;

    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountEntity> account = repository.findByUsername(username);
        if (account.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s is not found", username));
        }

        AccountEntity user = account.get();

        return UserDetailsImpl.builder()
                .account(user)
                .build();
    }

    @Override
    public boolean validatePasswords(String passwordOne, String passwordTwo) {
        return passwordOne.equals(passwordTwo);
    }

    @Override
    public UUID register(AccountRegistrationRequest request) {
        AccountEntity account = mapper.toEntity(request);
        account.setPassword(encoder.encode(account.getPassword()));
        account.setAbout("-");
        account.setAvatar("https://mirtex.ru/wp-content/uploads/2023/04/unnamed.jpg");
        account.setRole(new RoleEntity(2, "USER"));
        return repository.save(account).getUuid();
    }
}
