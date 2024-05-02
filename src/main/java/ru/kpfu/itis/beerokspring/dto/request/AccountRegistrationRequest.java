package ru.kpfu.itis.beerokspring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import ru.kpfu.itis.beerokspring.validator.ValidBirthday;
import ru.kpfu.itis.beerokspring.validator.ValidEmail;
import ru.kpfu.itis.beerokspring.validator.ValidUsername;

import java.util.Date;

public record AccountRegistrationRequest(

        @Size(min = 6, max = 255, message = "Длина логина должна быть больше 6 и меньше 255")
        @NotBlank(message = "Логин не может быть пустым")
        @ValidUsername
        String username,

        @Size(min = 2, max = 255, message = "Длина имени должна быть больше 2 и меньше 255")
        @NotBlank(message = "Имя не может быть пустым")
        String name,

        @Size(min = 2, max = 255, message = "Длина фамилии должна быть больше 2 и меньше 255")
        @NotBlank(message = "Фамилия не может быть пустой")
        String lastname,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Дата не может быть пустой")
        @ValidBirthday
        Date birthday,

        @ValidEmail
        @NotBlank(message = "Фамилия не может быть пустой")
        String email,

        @Size(min = 6, max = 255, message = "Длина пароля должна быть больше 2 и меньше 255")
        @NotBlank(message = "Пароль не может быть пустым")
        String password,

        @Size(min = 6, max = 255, message = "Длина пароля должна быть больше 2 и меньше 255")
        @NotBlank(message = "Пароль не может быть пустым")
        String repeatPassword
) {
}
