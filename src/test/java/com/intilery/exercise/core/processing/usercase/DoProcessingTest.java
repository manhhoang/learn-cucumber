package com.intilery.exercise.core.processing.usercase;

import com.intilery.exercise.core.processing.usecase.DoProcessing;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DoProcessingTest {

    @InjectMocks
    DoProcessing doProcessing;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoMultiThreadedProcess() throws Exception {
        final int numberThreads = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(numberThreads);
        final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numberThreads);
        final CountDownLatch afterInitBlocker = new CountDownLatch(1);
        final CountDownLatch allDone = new CountDownLatch(numberThreads);
        for (int i = 0; i < numberThreads; i++) {
            executorService.submit(() -> {
                allExecutorThreadsReady.countDown();
                try {
                    afterInitBlocker.await();
                    doProcessing.doMultiThreadedProcess("TaskName");
                } catch (final Exception e) {
                } finally {
                    allDone.countDown();
                }
            });
        }
        // wait until all threads are ready
        allExecutorThreadsReady.await(numberThreads * 10, TimeUnit.MILLISECONDS);
        // start all test runners
        afterInitBlocker.countDown();
        allDone.await(30, TimeUnit.SECONDS);
        executorService.shutdownNow();
        assertEquals("100", doProcessing.getMap().get("TaskName").toString());
    }
}
