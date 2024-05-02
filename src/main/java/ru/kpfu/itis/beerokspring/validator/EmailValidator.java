package ru.kpfu.itis.beerokspring.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;

import java.util.Optional;

import static ru.kpfu.itis.beerokspring.util.Constants.EMAIL_REGEX;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private final AccountRepository repository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<AccountEntity> account = repository.findByEmail(value);
        return account.isEmpty() && value.matches(EMAIL_REGEX);
    }
}
