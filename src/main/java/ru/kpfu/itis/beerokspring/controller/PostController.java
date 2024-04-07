package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kpfu.itis.beerokspring.service.PostService;

import java.util.UUID;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public String postList(Model model) {
        model.addAttribute("post", service.getAll());
        return "view/post/listPost";
    }

    @GetMapping("/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String postDetail(@PathVariable("id") UUID id, Model model) {
        return null;
    }
}
