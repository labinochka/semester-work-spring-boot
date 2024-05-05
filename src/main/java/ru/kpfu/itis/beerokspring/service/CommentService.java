package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.response.CommentResponse;

import java.util.UUID;

public interface CommentService {

    CommentResponse create(UUID postId, String comment, String authorUsername);

    UUID delete(UUID id, String authorUsername);
}
