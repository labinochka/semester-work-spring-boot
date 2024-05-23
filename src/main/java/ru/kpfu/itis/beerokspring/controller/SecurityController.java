package ru.kpfu.itis.beerokspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;;
import ru.kpfu.itis.beerokspring.service.AccountService;
import ru.kpfu.itis.beerokspring.service.VerificationTokenService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final AccountService accountService;

    private final VerificationTokenService tokenService;

    @GetMapping("/sign-in")
    public String signIn(@RequestParam(required = false) boolean error, Model model) {
        if (error) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "view/security/signIn";
    }

    @GetMapping("/registration")
    public String registrationView() {
        return "view/security/registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("account") AccountRegistrationRequest request,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("error", errorMessages);
            model.addAttribute("username", request.username());
            model.addAttribute("name", request.name());
            model.addAttribute("lastname", request.lastname());
            model.addAttribute("email", request.email());
            model.addAttribute("password", request.password());
            model.addAttribute("repeatPassword", request.repeatPassword());
            return "view/security/registration";
        }
        if (!accountService.validatePasswords(request.password(), request.repeatPassword())) {
            model.addAttribute("error", "Пароли не совпадают");
            return "view/security/registration";
        }
        UUID id = accountService.register(request);
        tokenService.sendEmail(id);
        return "redirect:/sign-in";
    }

    @GetMapping("/no-access")
    public String noAccessView() {
        return "view/error/noAccess";
    }
}
