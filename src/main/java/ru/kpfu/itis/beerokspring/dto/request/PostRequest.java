package ru.kpfu.itis.beerokspring.dto.request;

public record PostRequest(
        String title,

        String content,

        String image
) {
}
