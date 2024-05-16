package ru.kpfu.itis.beerokspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.beerokspring.dto.request.AccountUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.AccountService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping("/someone")
    public String getSomeoneProfile(@RequestParam("username") String username, Model model) {
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
    public String editView(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        AccountResponse account = service.getByUsername(userDetails.getUsername());
        if (account != null) {
            model.addAttribute("account", account);
            return "view/profile/editProfile";
        }
        return "view/error/notFound";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("account") AccountUpdateRequest request, BindingResult result,
                       Model model, Principal principal, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = principal.getName();
        String res;
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("error", errorMessages);
            return "view/profile/editProfile";
        }
        String name = service.getByUsername(username).username();
        res = service.validate(name, request);
        if (res == null) {
            userDetails.setAccount(service.edit(name, request));
            return "redirect:/account/profile";
        }
        model.addAttribute("error", res);
        return "view/profile/editProfile";
    }
}
