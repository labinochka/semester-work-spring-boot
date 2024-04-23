package ru.kpfu.itis.beerokspring.dto.request;

public record AccountSignInRequest(
        String login,

        String password
) {
}
