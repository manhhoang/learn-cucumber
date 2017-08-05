package com.intilery.exercise.core.greeter.usecase;

import com.google.common.base.Strings;
import com.intilery.exercise.core.greeter.domain.Greeting;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateGreeting {
    private final String template = "Hello %s!";
    private final String defaultName = "World";

    public Greeting createGreetingTo(String name) {
        String to = Strings.isNullOrEmpty(name) ? defaultName : name;
        final String value = String.format(template, to);
        return new Greeting(value);
    }
}
