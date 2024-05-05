package ru.kpfu.itis.beerokspring.dto.response;

import java.util.Date;
import java.util.UUID;

public record CommentResponse(

        UUID uuid,

        String content,

        Date dateOfPublication,

        ShortInfoAccountResponse author
) {
}
