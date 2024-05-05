package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoPostResponse;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface PostService {

    void create(PostRequest request, String authorUsername);

    List<ShortInfoPostResponse> getAll();

    List<ShortInfoPostResponse> getByTitle(String title);

    void deleteById(UUID uuid, String authorUsername);

    void edit(UUID id, PostRequest newPost, String authorUsername);

    PostResponse getById(UUID uuid);

}
