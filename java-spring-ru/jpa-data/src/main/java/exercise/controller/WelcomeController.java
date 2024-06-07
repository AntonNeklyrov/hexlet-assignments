package exercise.controller;

import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WelcomeController {

    private final PersonRepository personRepository;

    public WelcomeController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(path = "")
    public String welcome() {
        return "Welcome to Spring!";
    }


    @GetMapping("/people")
    List<Person> getPersons () {
        return personRepository.findAll();
    }

    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping("/people/{id}")
    void deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
}
