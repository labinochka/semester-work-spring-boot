package ru.kpfu.itis.beerokspring.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record BeerUpdateRequest(
        UUID uuid,

        String sort,

        String type,

        MultipartFile image,

        String content
) {
}
