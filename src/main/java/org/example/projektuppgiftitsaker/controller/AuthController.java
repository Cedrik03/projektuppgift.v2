package org.example.projektuppgiftitsaker.controller;

import jakarta.validation.Valid;
import org.example.projektuppgiftitsaker.dto.UserRegistrationDto;
import org.example.projektuppgiftitsaker.exception.UserNotFoundException;
import org.example.projektuppgiftitsaker.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AppUserService service;

    @Autowired
    public AuthController(AppUserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationDto dto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        service.register(dto);
        return "redirect:/";
    }

    // Ta bort den gamla POST-rutten som inte är nödvändig längre
    /*@PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        service.deleteUser(id);
        return "redirect:/";
    }*/

    // DELETE-metod för att ta bort användare baserat på användarnamn
    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        service.deleteUserByUsername(username);  // Anropa service-metoden för att ta bort användare
        return ResponseEntity.noContent().build();  // Returnera 204 No Content vid framgång
    }

    // Hantera undantag om användaren inte finns
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
