package org.example.projektuppgiftitsaker.dto;

import jakarta.validation.constraints.*;

public class UserRegistrationDto {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2})(?=(?:.*[!@#$%&*]){2}).{8,}$",
            message = "Lösenordet måste vara minst 8 tecken, ha 1 stor bokstav, 2 siffror och 2 specialtecken (!@#$%&*)"
    )
    private String password;

    @NotBlank
    private String role;

    private boolean consentGiven;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isConsentGiven() { return consentGiven; }
    public void setConsentGiven(boolean consentGiven) { this.consentGiven = consentGiven; }

}
