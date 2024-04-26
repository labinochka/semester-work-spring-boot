package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;;
import ru.kpfu.itis.beerokspring.service.AuthService;
import ru.kpfu.itis.beerokspring.service.VerificationTokenService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final AuthService authService;

    private final VerificationTokenService tokenService;

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
        String res = authService.validate(request);
        if (res != null) {
            model.addAttribute("error", res);
            return "view/security/registration";
        }
        UUID id = authService.register(request);
        tokenService.sendEmail(id);
        return "redirect:/sign-in";
    }
}
