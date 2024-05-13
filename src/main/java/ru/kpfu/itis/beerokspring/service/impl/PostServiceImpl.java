package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoPostResponse;
import ru.kpfu.itis.beerokspring.exception.AccountNotFoundException;
import ru.kpfu.itis.beerokspring.exception.NoAccessException;
import ru.kpfu.itis.beerokspring.exception.PostNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.PostMapper;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.PostEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;
import ru.kpfu.itis.beerokspring.repository.PostRepository;
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

    private final PostRepository postRepository;

    private final AccountRepository accountRepository;

    private final PostMapper mapper;

    @Override
    public void create(PostRequest request, String authorUsername) {
        AccountEntity author = accountRepository.findByUsername(authorUsername).get();
        PostEntity post = mapper.toEntity(request);
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString();
        post.setUuid(uuid);
        post.setImage(FileUploaderUtil.uploadFile(request.image(), fileName, urlPosts));
        post.setDateOfPublication(new Date());
        post.setAuthor(author);
        postRepository.save(post);
    }


    @Override
    public List<ShortInfoPostResponse> getAll() {
        return mapper.toResponse(postRepository.findAll());
    }

    @Override
    public List<ShortInfoPostResponse> getByTitle(String title) {
        String new_title = '%' + title.toLowerCase() + "%";
        return mapper.toResponse(postRepository.findAllByTitle(new_title));
    }

    @Override
    public void deleteById(UUID uuid, String authorUsername) {
        try {
            PostEntity post = postRepository.findById(uuid).orElseThrow(PostNotFoundException::new);
            AccountEntity author = accountRepository.findByUsername(authorUsername)
                    .orElseThrow(AccountNotFoundException::new);
            if (author.getUuid().equals(post.getAuthor().getUuid())) {
                FileUploaderUtil.deleteFile(post.getImage());
                postRepository.delete(post);
            } else {
                throw new NoAccessException();
            }
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", uuid, e);
            throw e;
        } catch (NoAccessException e) {
            log.error("The post has not been deleted for id: {}", uuid, e);
            throw e;
        } catch (AccountNotFoundException e) {
            log.error("Account not found for username: {}", authorUsername, e);
            throw e;
        }
    }

    @Override
    public void edit(UUID id, PostRequest newPost, String authorUsername) {
        try {
            PostEntity post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
            AccountEntity author = accountRepository.findByUsername(authorUsername)
                    .orElseThrow(AccountNotFoundException::new);
            if (author.getUuid().equals(post.getAuthor().getUuid())) {
                post.setTitle(newPost.title());
                post.setContent(newPost.content());
                if (!Objects.requireNonNull(newPost.image().getOriginalFilename()).isEmpty()) {
                    FileUploaderUtil.deleteFile(post.getImage());
                    String fileName = post.getUuid().toString();
                    post.setImage(FileUploaderUtil.uploadFile(newPost.image(), fileName, urlPosts));
                }
                postRepository.save(post);
            } else {
                throw new NoAccessException();
            }
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", id, e);
            throw e;
        } catch (NoAccessException e) {
            log.error("The post has not been edited for id: {}", id, e);
            throw e;
        } catch (AccountNotFoundException e) {
            log.error("Account not found for username: {}", authorUsername, e);
            throw e;
        }
    }

    @Override
    public PostResponse getById(UUID uuid) {
        try {
            return mapper.toResponse(postRepository.findById(uuid)
                    .orElseThrow(PostNotFoundException::new));
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", uuid, e);
            throw e;
        }

    }
}
