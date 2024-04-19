package ru.kpfu.itis.beerokspring.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kpfu.itis.beerokspring.exception.NotFoundServiceException;

@Controller
public class MainController {

    private static final String ERROR_PATH = "/error";

    @GetMapping("/about")
    @ResponseStatus(HttpStatus.OK)
    public String mainView() {
        return "/view/about/about";
    }

    @GetMapping(ERROR_PATH)
    public String handleErrors() {
        throw new NotFoundServiceException("Page not found");
    }
}
