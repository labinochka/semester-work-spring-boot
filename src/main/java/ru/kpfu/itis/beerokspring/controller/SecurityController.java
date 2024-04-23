package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;
import ru.kpfu.itis.beerokspring.dto.request.AccountSignInRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.service.AuthService;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final AuthService service;

    @GetMapping("/sign-in")
    public String signIn(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "view/security/signIn";
    }

    @GetMapping("/registration")
    public String registrationView() {
        return "view/security/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("account") AccountRegistrationRequest request, Model model) {
        String res = service.register(request);
        if (res != null) {
            model.addAttribute("error", res);
            return "view/security/registration";
        }
        return "redirect:/sign-in";
    }
}
