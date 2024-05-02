package ru.kpfu.itis.beerokspring.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {

    String message() default "Логин указан в неправильном формате или такой логин уже существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default  {};
}
