package org.example.projektuppgiftitsaker.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Användare med ID " + id + " finns inte.");
    }
}
