package ru.kpfu.itis.beerokspring.dto.response;

public record PostResponse(

        ShortInfoAccountResponse author,

        String title,

        String content,

        String image,

        String dateOfPublication
) {
}
