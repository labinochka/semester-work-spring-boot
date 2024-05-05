package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.kpfu.itis.beerokspring.service.CommentService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class RestCommentController {

    private final CommentService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("postId") UUID id, @RequestParam("content") String content,
                                    Principal principal) {
        return ResponseEntity.ok(service.create(id, content, principal.getName()));
    }
}
