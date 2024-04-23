package ru.kpfu.itis.beerokspring.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record AccountRegistrationRequest(
        String username,

        String name,

        String lastname,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date birthday,

        String email,

        String password,

        String repeatPassword
) {
}
