package ru.kpfu.itis.beerokspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping("/about")
    public String mainView() {
        return "/view/about/about";
    }

}
