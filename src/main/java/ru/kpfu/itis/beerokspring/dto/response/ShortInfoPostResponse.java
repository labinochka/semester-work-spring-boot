package ru.kpfu.itis.beerokspring.dto.response;

import java.util.UUID;

public record ShortInfoPostResponse(

        UUID uuid,

        String title
) {
}
