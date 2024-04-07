package ru.kpfu.itis.beerokspring.dto.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PostResponse(

        UUID uuid,

        ShortInfoAccountResponse author,

        String title,

        String content,

        String image,

        Date dateOfPublication,

        List<CommentResponse> comments
) {
}
