package ru.kpfu.itis.beerokspring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record PostRequest(

        @Size(min = 6, max = 255, message = "Длина названия должна быть больше 6 и меньше 255")
        @NotBlank(message = "Название не может быть пустым")
        String title,

        @Size(min = 2, max = 255, message = "Длина названия должна быть больше 2 и меньше 255")
        @NotBlank(message = "Описание не может быть пустым")
        String content,

        @NotNull
        MultipartFile image
) {
}
