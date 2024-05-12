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
import org.springframework.web.bind.annotation.*;

import ru.kpfu.itis.beerokspring.dto.response.CommentResponse;
import ru.kpfu.itis.beerokspring.service.CommentService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Tags(value = {
        @Tag(name = "Comments")
})
@Schema(description = "Работа с комментариями")
public class RestCommentController {

    private final CommentService service;

    @Operation(summary = "Создание комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Комментарий создан",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
            @ApiResponse(responseCode = "401", description = "Не пройдена авторизация"),
            @ApiResponse(responseCode = "403", description = "Не достаточно прав"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("postId") UUID id, String content,
                                    Principal principal) {
        if (content.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.create(id, content, principal.getName()));
    }

    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий удален",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UUID.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
            @ApiResponse(responseCode = "401", description = "Не пройдена авторизация"),
            @ApiResponse(responseCode = "403", description = "Не достаточно прав"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") UUID id, Principal principal) {
        return ResponseEntity.ok(service.delete(id, principal.getName()));
    }
}
