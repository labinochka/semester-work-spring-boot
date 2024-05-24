package ru.kpfu.itis.beerokspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.CommentResponse;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.service.AccountService;
import ru.kpfu.itis.beerokspring.service.PostService;

import java.security.Principal;
import java.util.List;
import java.util.Comparator;
import java.util.UUID;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final AccountService accountService;

    @GetMapping("/list")
    public String postList(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("account", accountService.getByUsername(principal.getName()));
        }
        model.addAttribute("post", postService.getAll());
        return "view/post/listPost";
    }

    @GetMapping("/detail")
    public String postDetail(@RequestParam("id") UUID id, Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("account", accountService.getByUsername(principal.getName()));
        }
        PostResponse post = postService.getById(id);
        List<CommentResponse> comments = post.comments();
        comments.sort(Comparator.comparing(CommentResponse::dateOfPublication).reversed());
        model.addAttribute("post", post);
        model.addAttribute("comment", comments);
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
            model.addAttribute("title", post.title());
            model.addAttribute("content", post.content());
            return "view/post/createPost";
        }
        String author = principal.getName();
        postService.create(post, author);
        return "redirect:/post/list";
    }

    @GetMapping("/edit")
    public String editView(@RequestParam("id") UUID id, Model model) {
        model.addAttribute("post", postService.getById(id));
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
            model.addAttribute("title", post.title());
            model.addAttribute("content", post.content());
            return "view/post/editPost";
        }
        String username = principal.getName();
        postService.edit(id, post, username);
        return "redirect:/post/detail?id=" + id;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") UUID id, Principal principal) {
        String username = principal.getName();
        postService.deleteById(id, username);
        return "redirect:/account/profile";
    }

    @GetMapping("/deleteAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAdmin(@RequestParam("id") UUID id) {
        postService.deleteById(id);
        return "redirect:/account/profile";
    }
}
