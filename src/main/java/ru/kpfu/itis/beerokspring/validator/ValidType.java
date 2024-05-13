package ru.kpfu.itis.beerokspring.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidType {

    String message() default "Такой тип уже есть";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default  {};
}
