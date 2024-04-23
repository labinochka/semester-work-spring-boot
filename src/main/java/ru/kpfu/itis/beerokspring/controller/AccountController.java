package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.AccountService;

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
}
