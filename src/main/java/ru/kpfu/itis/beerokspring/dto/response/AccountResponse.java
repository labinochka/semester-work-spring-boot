package ru.kpfu.itis.beerokspring.dto.response;

import java.util.List;
import java.util.UUID;

public record AccountResponse(

        UUID UUID,

        String username,

        String name,

        String lastname,

        String email,

        String avatar,

        String about,

        List<ShortInfoPostResponse> posts
) {
}
