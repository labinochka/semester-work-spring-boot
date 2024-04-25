package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.beerokspring.service.VerificationTokenService;

@Controller
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationTokenService tokenService;

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        if (tokenService.verify(token)) {
            return "view/verify/succes";
        }
        return "view/verify/failed";
    }
}
