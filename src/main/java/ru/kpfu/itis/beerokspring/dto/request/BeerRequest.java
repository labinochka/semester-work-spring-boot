package ru.kpfu.itis.beerokspring.dto.request;

public record BeerRequest(
        String sort,

        String type,

        String image,

        String content
) {
}
