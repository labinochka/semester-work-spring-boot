package ru.kpfu.itis.beerokspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.CommentResponse;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.service.PostService;

import java.security.Principal;
import java.util.Collections;
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

    @GetMapping("/detail")
    @ResponseStatus(HttpStatus.OK)
    public String postDetail(@RequestParam("id") UUID id, Model model, Principal principal) {
        String username = principal.getName();
        PostResponse post = service.getById(id);
        List<CommentResponse> comments = post.comments();
        Collections.reverse(comments);
        model.addAttribute("post", post);
        model.addAttribute("comment", comments);
        model.addAttribute("username", username);
        return "view/post/detailPost";
    }

    @GetMapping("/create")
    public String createView() {
        return "view/post/createPost";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("post") PostRequest post, BindingResult result, Model model,
                         Principal principal) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("error", errorMessages);
            return "view/post/createPost";
        }
        String author = principal.getName();
        service.create(post, author);
        return "redirect:/post/list";
    }

    @GetMapping("/edit")
    public String editView(@RequestParam("id") UUID id, Model model) {
        model.addAttribute("post", service.getById(id));
        return "view/post/editPost";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("id") UUID id, @Valid @ModelAttribute("post") PostRequest post,
                       BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("error", errorMessages);
            return "view/post/editPost";
        }
        String username = principal.getName();
        service.edit(id, post, username);
        return "redirect:/post/detail?id=" + id;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") UUID id, Principal principal) {
        String username = principal.getName();
        service.deleteById(id, username);
        return "redirect:/account/profile";
    }
}
