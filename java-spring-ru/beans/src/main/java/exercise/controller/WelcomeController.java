package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    private final Daytime daytime;

    public WelcomeController(Daytime daytime) {
        this.daytime = daytime;
    }

    @GetMapping
    String getWelcome() {
        return "It is " + daytime.getName() + "now! Welcome to Spring!";
    }
}

