package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.VerificationTokenService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationTokenService service;

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public String verifyEmail(@RequestParam("token") String token) {
        if (service.verify(token)) {
            return "view/verify/succes";
        }
        return "view/verify/failed";
    }

    @GetMapping("/send")
    public String sendEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            if (!userDetails.getAccount().isVerified()) {
                UUID userId = userDetails.getAccount().getUuid();
                service.sendEmail(userId);
                return "view/verify/send";
            }
        }
        return "view/verify/failed";
    }
}
