package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.response.CommentResponse;
import ru.kpfu.itis.beerokspring.exception.AccountNotFoundException;
import ru.kpfu.itis.beerokspring.exception.CommentNotFoundException;
import ru.kpfu.itis.beerokspring.exception.NoAccessException;
import ru.kpfu.itis.beerokspring.exception.PostNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.CommentMapper;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.CommentEntity;
import ru.kpfu.itis.beerokspring.model.PostEntity;
import ru.kpfu.itis.beerokspring.repository.AccountRepository;
import ru.kpfu.itis.beerokspring.repository.CommentRepository;
import ru.kpfu.itis.beerokspring.repository.PostRepository;
import ru.kpfu.itis.beerokspring.service.CommentService;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final AccountRepository accountRepository;

    private final CommentMapper mapper;

    @Override
    public CommentResponse create(UUID postId, String comment, String authorUsername) {
        CommentEntity entity = new CommentEntity();
        try {
            AccountEntity author = accountRepository.findByUsername(authorUsername)
                    .orElseThrow(AccountNotFoundException::new);
            PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
            entity.setContent(comment);
            entity.setDateOfPublication(new Date());
            entity.setAuthor(author);
            entity.setPost(post);
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", postId, e);
            throw e;
        } catch (AccountNotFoundException e) {
            log.error("Account not found for username: {}", authorUsername, e);
            throw e;
        }
        return mapper.toResponse(commentRepository.save(entity));
    }

    @Override
    public UUID delete(UUID id, String authorUsername) {
        try {
            CommentEntity comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
            AccountEntity author = accountRepository.findByUsername(authorUsername)
                    .orElseThrow(AccountNotFoundException::new);
            if (author.getUuid().equals(comment.getAuthor().getUuid()) ||
                    author.getRole().getName().equals("ROLE_ADMIN")) {
                commentRepository.delete(comment);
            } else {
                throw new NoAccessException();
            }
        } catch (CommentNotFoundException e) {
            log.error("Comment not found for id: {}", id, e);
            throw e;
        } catch (NoAccessException e) {
            log.error("The comment has not been deleted for id: {}", id, e);
            throw e;
        } catch (AccountNotFoundException e) {
            log.error("Account not found for username: {}", authorUsername, e);
            throw e;
        }
        return id;
    }
}
