package ru.kpfu.itis.beerokspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoPostResponse;
import ru.kpfu.itis.beerokspring.service.PostService;

import java.util.List;

@RestController
@Tags(value = {
        @Tag(name = "Posts")
})
@Schema(description = "Поиск постов")
@RequiredArgsConstructor
public class RestSearchController {

    private final PostService service;

    @Operation(summary = "Поиск постов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посты получены",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/searching")
    public ResponseEntity<?> search(@RequestParam("title") String title) {
        List<ShortInfoPostResponse> posts = service.getByTitle(title);
        return ResponseEntity.ok(posts);
    }
}