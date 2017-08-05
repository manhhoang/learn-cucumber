package com.intilery.exercise.web.facade;

import com.intilery.exercise.core.greeter.domain.Greeting;
import com.intilery.exercise.core.greeter.usecase.CreateGreeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/hello-world")
public class GreetingResource {
    private final CreateGreeting createGreeting;

    @Autowired
    public GreetingResource(CreateGreeting createGreeting) {
        this.createGreeting = createGreeting;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting sayGreeting(@RequestParam("name") final Optional<String> name) {
        return createGreeting.createGreetingTo(name.orElse(null));
    }
}