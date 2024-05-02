package ru.kpfu.itis.beerokspring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record AccountUpdateRequest(

        @Size(min = 6, max = 255, message = "Длина логина должна быть больше 6 и меньше 255")
        @NotBlank(message = "Логин не может быть пустым")
        String username,

        @Size(min = 2, max = 255, message = "Длина имени должна быть больше 2 и меньше 255")
        @NotBlank(message = "Имя не может быть пустым")
        String name,

        @Size(min = 2, max = 255, message = "Длина фамилии должна быть больше 2 и меньше 255")
        @NotBlank(message = "Фамилия не может быть пустой")
        String lastname,

        @NotBlank(message = "Фамилия не может быть пустой")
        String email,

        @NotBlank(message = "Напишите что-то о себе")
        String about,

        MultipartFile avatar

) {
}
