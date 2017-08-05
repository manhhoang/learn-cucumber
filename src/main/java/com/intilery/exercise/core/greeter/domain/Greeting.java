package com.intilery.exercise.core.greeter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {
    private String message;

    public Greeting() {
        // Jackson deserialization
    }

    public Greeting(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getGreeting() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
