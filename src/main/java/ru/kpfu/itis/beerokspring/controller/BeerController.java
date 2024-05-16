package ru.kpfu.itis.beerokspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.beerokspring.dto.request.BeerRequest;
import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.service.AccountService;
import ru.kpfu.itis.beerokspring.service.BeerService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/beer")
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;

    private final AccountService accountService;

    @GetMapping("/list")
    public String listView(Model model, Principal principal) {
        model.addAttribute("beersAle", beerService.getBeersBySort("Эль"));
        model.addAttribute("beersLager", beerService.getBeersBySort("Лагер"));
        model.addAttribute("beersMixed", beerService.getBeersBySort("Смешанное"));
        if (principal != null) {
            model.addAttribute("account", accountService.getByUsername(principal.getName()));
        }
        return "view/beer/listBeer";
    }

    @GetMapping("/detail")
    public String detailView(@RequestParam("id") UUID id, Model model) {
        model.addAttribute("beer", beerService.getById(id));
        return "view/beer/detailBeer";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addBeerView(@RequestParam("sort") String sort, Model model) {
        model.addAttribute("sort", sort);
        return "view/beer/addBeer";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addBeer(@RequestParam("sort") String sort, @Valid @ModelAttribute("beer") BeerRequest request,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("error", errorMessages);
            model.addAttribute("type", request.type());
            model.addAttribute("content", request.content());
            model.addAttribute("sort", sort);
            return "view/beer/addBeer";
        }
        beerService.add(request, sort);
        return "redirect:/beer/list";
    }
}
