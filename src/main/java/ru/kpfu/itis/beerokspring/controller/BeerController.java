package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kpfu.itis.beerokspring.service.BeerService;

import java.util.UUID;

@Controller
@RequestMapping("/beer")
@RequiredArgsConstructor
public class BeerController {

    private final BeerService service;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public String listView(Model model) {;
        model.addAttribute("beersAle", service.getBeersBySort("Эль"));
        model.addAttribute("beersLager", service.getBeersBySort("Лагер"));
        model.addAttribute("beersMixed", service.getBeersBySort("Смешанное"));
        return "view/beer/listBeer";
    }

    @GetMapping("/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String detailView(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("beer", service.getById(id));
        return "view/beer/detailBeer";
    }
}
