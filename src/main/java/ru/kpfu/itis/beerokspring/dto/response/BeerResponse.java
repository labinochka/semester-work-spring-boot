package ru.kpfu.itis.beerokspring.dto.response;

import java.util.UUID;

public record BeerResponse(

        UUID uuid,

        String type,

        String image,

        String content
) {
}