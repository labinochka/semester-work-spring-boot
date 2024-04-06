package ru.kpfu.itis.beerokspring.dto.response;

import java.util.UUID;

public record BeerResponse(
        UUID uuid,

        String sort,

        String type,

        String image,

        String content
) {
}
