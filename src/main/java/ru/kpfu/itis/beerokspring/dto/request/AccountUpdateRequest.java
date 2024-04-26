package ru.kpfu.itis.beerokspring.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record AccountUpdateRequest(
        String username,

        String name,

        String lastname,

        String email,

        String about,

        MultipartFile avatar

) {
}
