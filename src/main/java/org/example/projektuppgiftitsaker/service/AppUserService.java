package org.example.projektuppgiftitsaker.service;

import org.example.projektuppgiftitsaker.component.LoggingComponent;
import org.example.projektuppgiftitsaker.dto.UserRegistrationDto;
import org.example.projektuppgiftitsaker.entity.AppUser;
import org.example.projektuppgiftitsaker.exception.UserNotFoundException;
import org.example.projektuppgiftitsaker.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final LoggingComponent logger;

    @Autowired
    public AppUserService(AppUserRepository repository, PasswordEncoder passwordEncoder, LoggingComponent logger) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
    }

    public void register(UserRegistrationDto dto) {
        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_" + dto.getRole().toUpperCase());
        user.setConsentGiven(dto.isConsentGiven());
        repository.save(user);
        logger.log("Registrerat användare: " + user.getUsername());
    }

    public void deleteUser(Long id) {
        AppUser user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        repository.delete(user);
        logger.log("Tog bort användare med ID: " + id);
    }

    public void deleteUserByUsername(String testuser) {
    }
}
