package com.jd.tinkerpop.core.processing.usecase;

import com.jd.tinkerpop.core.processing.repository.LongRunningLockedProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DoProcessing {
    private final LongRunningLockedProcess longRunningLockedProcess;
    private final Map<String, Integer> map = new ConcurrentHashMap<>();

    @Autowired
    public DoProcessing(LongRunningLockedProcess longRunningLockedProcess) {
        this.longRunningLockedProcess = longRunningLockedProcess;
    }

    public void doLongRunningSerialProcess() {
        CompletableFuture.runAsync(() -> longRunningLockedProcess.doSomething());
    }

    public Integer doMultiThreadedProcess(String name) {
        map.putIfAbsent(name, 0);
        return map.merge(name, 1, Integer::sum);
    }

    public Map<String, Integer> getMap() {
        return map;
    }
}
