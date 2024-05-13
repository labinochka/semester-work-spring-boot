package ru.kpfu.itis.beerokspring.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.beerokspring.model.BeerEntity;
import ru.kpfu.itis.beerokspring.repository.BeerRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class TypeValidator implements ConstraintValidator<ValidType, String> {

    private final BeerRepository repository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<BeerEntity> beer = repository.findByType(value);
        return beer.isEmpty();
    }
}
