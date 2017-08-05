package com.intilery.exercise.core.processing.repository;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LongRunningLockedProcess {
    private static final Object lock = new Object();
    private final Random random = new Random();

    public void doSomething() {
        synchronized (lock) {
            try {
                // some process happens here, it always takes at least a second
                long wait = 1000 + random.nextInt(5000);
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                // killed
            }
        }
    }
}
