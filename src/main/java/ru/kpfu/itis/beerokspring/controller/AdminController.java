package ru.kpfu.itis.beerokspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.beerokspring.security.details.UserDetailsImpl;
import ru.kpfu.itis.beerokspring.service.AdminService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService service;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String listView(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("admins", service.getAdmins(userDetails.getAccount().getUsername()));
        return "view/admin/listAdmin";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addAdmin(String username) {
        service.addAdmin(username);
        return "redirect:/admin/list";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAdmin(@RequestParam("username") String username, Principal principal) {
        service.deleteAdmin(username);
        if (username.equals(principal.getName())) {
            return "redirect:/account/profile";
        }
        return "redirect:/admin/list";
    }

}
