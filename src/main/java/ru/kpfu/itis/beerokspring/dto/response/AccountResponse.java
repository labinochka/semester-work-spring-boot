package ru.kpfu.itis.beerokspring.dto.response;

import java.util.List;

public record AccountResponse(

        String username,

        String name,

        String lastname,

        String avatar,

        String about,

        List<ShortInfoPostResponse> posts
) {
}
