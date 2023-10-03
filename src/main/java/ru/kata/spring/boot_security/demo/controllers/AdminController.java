package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPage(Principal principal, Model model) {

        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("authUser", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.findAll());
        return "admin/index";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("newUser") @Valid User newUser, BindingResult bindingResult) {

        userService.save(newUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/users/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PatchMapping("/users/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             @PathVariable("id") long id) {
        userService.update(user, id);
        return "redirect:/admin";
    }
}
