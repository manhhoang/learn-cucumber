package com.jd.tinkerpop.core.greeter.usecase;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CreateGreetingTest {
    @Test
    public void helloWorldIfUnknownSpecified() {
        testGreeting(null, "Hello World!");
    }

    @Test
    public void helloWorldIfUnamedSpecified() {
        testGreeting("", "Hello World!");
    }

    @Test
    public void canGreetByName() {
        testGreeting("Me", "Hello Me!");
    }

    private void testGreeting(String to, String expectedGreeting) {
        CreateGreeting greeting = new CreateGreeting();
        assertThat(greeting.createGreetingTo(to).getGreeting(), is(expectedGreeting));
    }
}