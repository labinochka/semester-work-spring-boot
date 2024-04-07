package ru.kpfu.itis.beerokspring.dto.response;

import java.util.Date;

public record CommentResponse(

        String content,

        Date dateOfPublication,

        ShortInfoAccountResponse author
) {
}
