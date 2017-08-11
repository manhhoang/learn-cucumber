package com.intilery.exercise.core.processing.usecase;

import com.intilery.exercise.core.processing.repository.LongRunningLockedProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class DoProcessing {
    private final LongRunningLockedProcess longRunningLockedProcess;
    private final Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
    private final Object lock = new Object();

    @Autowired
    public DoProcessing(LongRunningLockedProcess longRunningLockedProcess) {
        this.longRunningLockedProcess = longRunningLockedProcess;
    }

    public void doLongRunningSerialProcess() {
        longRunningLockedProcess.doSomething();
    }

    public Integer doMultiThreadedProcess(String name) {
        synchronized (lock) {
            if (!map.containsKey(name)) {
                map.put(name, 1);
            } else {
                map.put(name, map.get(name) + 1);
            }
        }
        return map.get(name);
    }

    public Map<String, Integer> getMap() {
        return map;
    }
}
