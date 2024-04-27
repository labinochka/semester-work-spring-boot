package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.beerokspring.dto.request.AccountUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.AccountService;

import java.util.UUID;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping("/someone/{username}")
    public String getSomeoneProfile(@PathVariable("username") String username, Model model) {
        model.addAttribute("account", service.getByUsername(username));
        return "view/profile/someoneProfile";
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        AccountResponse account = service.getByUsername(userDetails.getUsername());
        if (account != null) {
            model.addAttribute("account", account);
            model.addAttribute("post", account.posts());
        }
        return "view/profile/profile";
    }

    @GetMapping("/edit")
    public String editView(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            model.addAttribute("account", service.getByUsername(userDetails.getAccount().getUsername()));
            return "view/profile/editProfile";
        }
        return "view/error/notFound";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("account") AccountUpdateRequest request, Model model) {
        String res = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            UUID id = userDetails.getAccount().getUuid();
            res = service.validate(id, request);
            if (res == null) {
                service.edit(id, request);
                return "redirect:/account/profile";
            }
        }
        model.addAttribute("error", res);
        return "view/profile/editProfile";
    }
}
