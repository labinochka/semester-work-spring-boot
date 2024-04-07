package ru.kpfu.itis.beerokspring.dto.response;

import java.util.UUID;

public record ShortInfoBeerResponse(

        UUID uuid,

        String type
) {
}
