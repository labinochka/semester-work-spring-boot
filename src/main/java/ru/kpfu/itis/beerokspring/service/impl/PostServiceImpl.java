package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoPostResponse;
import ru.kpfu.itis.beerokspring.exception.PostNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.PostMapper;
import ru.kpfu.itis.beerokspring.repository.PostRepository;
import ru.kpfu.itis.beerokspring.service.PostService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    private final PostMapper mapper;

    @Override
    public void add(PostRequest post) {
        repository.save(mapper.toEntity(post));
    }

    @Override
    public List<ShortInfoPostResponse> getAll() {
        return mapper.toResponse(repository.findAll());
    }

    @Override
    public List<ShortInfoPostResponse> getByTitle(String title) {
        String new_title = '%' + title + "%";
        return mapper.toResponse(repository.findAllByTitle(new_title));
    }

    @Override
    public void deleteById(UUID uuid) {
        try {
            repository.deleteById(repository.findById(uuid)
                    .orElseThrow(PostNotFoundException::new).getUuid());
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", uuid, e);
            throw e;
        }

    }

    @Override
    public void updateById(UUID id, PostRequest newPost) {
        getById(id);
        repository.updateByUuid(newPost.title(), newPost.content(), newPost.image(), id);
    }

    @Override
    public PostResponse getById(UUID uuid) {
        try {
            return mapper.toResponse(repository.findById(uuid)
                    .orElseThrow(PostNotFoundException::new));
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", uuid, e);
            throw e;
        }

    }

    @Override
    public List<ShortInfoPostResponse> getByAuthorId(UUID uuid) {
        return mapper.toResponse(repository.findAllByAuthorUuid(uuid));
    }
}
