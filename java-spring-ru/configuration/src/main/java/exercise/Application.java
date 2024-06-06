package exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import exercise.component.UserProperties;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private UserProperties userProperties;

    // Все пользователи
    private final List<User> users = Data.getUsers();

    // BEGIN

    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        return user;
    }

    @GetMapping("/admins")
    List<String> admins() {
        List<User> admins = new ArrayList<>();
        List<String> adminEmails = userProperties.getAdmins();

        for (var user : users) {
            if (adminEmails.contains(user.getEmail())) {
                admins.add(user);
            }
        }

        return admins.stream().map(User::getName).toList();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
