package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.CommentResponse;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.service.PostService;

import java.util.List;
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
        PostResponse post = service.getById(id);
        List<CommentResponse> comments = post.comments();
        model.addAttribute("post", post);
        model.addAttribute("comment", comments);
        return "view/post/detailPost";
    }

    @GetMapping("/create")
    public String createView() {
        return "view/post/createPost";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("post") PostRequest post) {
        service.create(post);
        return "redirect:/post/list";
    }

    @GetMapping("/edit/{id}")
    public String editView(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("post", service.getById(id));
        return "view/post/editPost";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") UUID id, @ModelAttribute("post") PostRequest post) {
        service.edit(id, post);
        return "redirect:/post/detail/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return "redirect:/account/profile";
    }
}
