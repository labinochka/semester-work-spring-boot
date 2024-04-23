package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;
import ru.kpfu.itis.beerokspring.mapper.AccountMapper;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.RoleEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.AuthService;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository repository;

    private final AccountMapper mapper;

    private final PasswordEncoder encoder;

    private final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";

    private final String EMAIL_REGEX = "[A-z0-9_-]+@[A-z0-9_-]+.[a-z]+";

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
    public String register(AccountRegistrationRequest request) {
        String username = request.username();
        String email = request.email();
        if (repository.findByUsername(username).isPresent() || repository.findByEmail(email).isPresent()) {
            return "Пользователь с таким логином или почтой уже зарегистрирован";
        }

        if (!username.matches(USERNAME_REGEX)) {
            return "Логин должен состоять из латинских букв";
        }

        if (!email.matches(EMAIL_REGEX)) {
            return "Неверно указана почта";
        }

        String password = request.password();
        String repeatPassword = request.repeatPassword();
        if (!password.equals(repeatPassword)) {
            return "Пароли не совпадают";
        }

        if (password.length() < 5) {
            return "Слишком короткий пароль";
        }

        Date birthday = request.birthday();
        Date currentDate = new Date();
        if ((currentDate.getYear() - birthday.getYear()) < 18) {
            return "Вам нет 18";
        }
        AccountEntity account = mapper.toEntity(request);
        account.setPassword(encoder.encode(account.getPassword()));
        account.setAbout("-");
        account.setAvatar("https://mirtex.ru/wp-content/uploads/2023/04/unnamed.jpg");
        account.setRole(new RoleEntity(2, "USER"));
        repository.save(account);

        return null;
    }
}
