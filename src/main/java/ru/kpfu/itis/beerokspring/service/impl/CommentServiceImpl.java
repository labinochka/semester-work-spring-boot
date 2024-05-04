package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.response.CommentResponse;
import ru.kpfu.itis.beerokspring.exception.PostNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.CommentMapper;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.CommentEntity;
import ru.kpfu.itis.beerokspring.model.PostEntity;
import ru.kpfu.itis.beerokspring.repository.CommentRepository;
import ru.kpfu.itis.beerokspring.repository.PostRepository;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.CommentService;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final CommentMapper mapper;

    @Override
    public CommentResponse create(UUID postId, String comment) {
        CommentEntity entity = new CommentEntity();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();
            AccountEntity author;
            PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
            if (principal instanceof UserDetailsImpl userDetails) {
                author = userDetails.getAccount();
                entity.setContent(comment);
                entity.setDateOfPublication(new Date());
                entity.setAuthor(author);
                entity.setPost(post);
            }
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", postId, e);
            throw e;
        }
        return mapper.toResponse(commentRepository.save(entity));
    }
}
