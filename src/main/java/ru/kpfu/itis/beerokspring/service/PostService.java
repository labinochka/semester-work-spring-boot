package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoPostResponse;

import java.util.List;
import java.util.UUID;

public interface PostService {

    void add(PostRequest post);

    List<ShortInfoPostResponse> getAll();

    List<ShortInfoPostResponse> getByTitle(String title);

    void deleteById(UUID uuid);

    void updateById(UUID id, PostRequest newPost);

    PostResponse getById(UUID uuid);

    List<ShortInfoPostResponse> getByAuthorId(UUID uuid);
}
