package ru.kpfu.itis.beerokspring.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;


public class BirthdayValidator implements ConstraintValidator<ValidBirthday, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        Date currentDate = new Date();
        return (currentDate.getYear() - value.getYear()) >= 18;
    }
}
