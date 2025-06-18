package org.example.projektuppgiftitsaker.component;

import org.springframework.stereotype.Component;

@Component
public class LoggingComponent {

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
