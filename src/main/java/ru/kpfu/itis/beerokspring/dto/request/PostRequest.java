package ru.kpfu.itis.beerokspring.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record PostRequest(
        String title,

        String content,

        MultipartFile image
) {
}
