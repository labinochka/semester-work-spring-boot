package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoPostResponse;
import ru.kpfu.itis.beerokspring.exception.NoAccessException;
import ru.kpfu.itis.beerokspring.exception.PostNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.PostMapper;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.PostEntity;
import ru.kpfu.itis.beerokspring.repository.PostRepository;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.PostService;
import ru.kpfu.itis.beerokspring.util.FileUploaderUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    @Value("${upload.url.suffix.posts}")
    private String urlPosts;

    private final PostRepository repository;

    private final PostMapper mapper;

    @Override
    public void create(PostRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        AccountEntity author;
        if (principal instanceof UserDetailsImpl userDetails) {
            author = userDetails.getAccount();
            PostEntity post = mapper.toEntity(request);
            String fileName = request.title() + UUID.randomUUID();
            post.setImage(FileUploaderUtil.uploadAvatar(request.image(), fileName, urlPosts));
            post.setDateOfPublication(new Date());
            post.setAuthor(author);
            repository.save(post);
        }
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
            PostEntity post = repository.findById(uuid).orElseThrow(PostNotFoundException::new);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();
            AccountEntity author;
            if (principal instanceof UserDetailsImpl userDetails) {
                author = userDetails.getAccount();
                if (author.getUuid().equals(post.getAuthor().getUuid())) {
                    repository.delete(post);
                } else {
                    throw new NoAccessException();
                }
            }
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", uuid, e);
            throw e;
        } catch (NoAccessException e) {
            log.error("The post has not been deleted for id: {}", uuid, e);
            throw e;
        }

    }

    @Override
    public void edit(UUID id, PostRequest newPost) {
        try {
            PostEntity post = repository.findById(id).orElseThrow(PostNotFoundException::new);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();
            AccountEntity author;
            if (principal instanceof UserDetailsImpl userDetails) {
                author = userDetails.getAccount();
                if (author.getUuid().equals(post.getAuthor().getUuid())) {
                    post.setTitle(newPost.title());
                    post.setContent(newPost.content());
                    if (!Objects.requireNonNull(newPost.image().getOriginalFilename()).isEmpty()) {
                        String fileName = newPost.title() + UUID.randomUUID();
                        post.setImage(FileUploaderUtil.uploadAvatar(newPost.image(), fileName, urlPosts));
                    }
                    repository.save(post);
                } else {
                    throw new NoAccessException();
                }
            }
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", id, e);
            throw e;
        } catch (NoAccessException e) {
            log.error("The post has not been edited for id: {}", id, e);
            throw e;
        }
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
