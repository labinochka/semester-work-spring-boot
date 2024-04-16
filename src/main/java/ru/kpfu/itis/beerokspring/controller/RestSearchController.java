package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoPostResponse;
import ru.kpfu.itis.beerokspring.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestSearchController {

    private final PostService service;

    @GetMapping("/searching/{title}")
    public ResponseEntity<?> search(@PathVariable("title") String title) {
        List<ShortInfoPostResponse> posts = service.getByTitle(title);
        return ResponseEntity.ok(posts);
    }
}