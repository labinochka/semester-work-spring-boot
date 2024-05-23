package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountEntity> account = repository.findByUsername(username);
        if (account.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s is not found", username));
        }

        AccountEntity user = account.get();

        return ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl.builder()
                .account(user)
                .build();
    }
//
//    @Override
//    public boolean validatePasswords(String passwordOne, String passwordTwo) {
//        return passwordOne.equals(passwordTwo);
//    }
//
//    @Override
//    public UUID register(AccountRegistrationRequest request) {
//        AccountEntity account = mapper.toEntity(request);
//        account.setPassword(encoder.encode(account.getPassword()));
//        account.setAbout("-");
//        account.setAvatar("https://mirtex.ru/wp-content/uploads/2023/04/unnamed.jpg");
//        account.setRole(new RoleEntity(2, "USER"));
//        return repository.save(account).getUuid();
//    }
}
