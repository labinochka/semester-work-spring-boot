package ru.kpfu.itis.beerokspring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.beerokspring.validator.ValidType;

public record BeerRequest(
        @NotBlank(message = "Тип не может быть пустым")
        @Size(min = 3, message = "Длина должна быть больше 3")
        @ValidType
        String type,

        @NotNull
        MultipartFile image,

        @NotBlank(message = "Описание не может быть пустым")
        @Size(min = 100, message = "Длина должна быть больше 100")
        String content
) {
}
