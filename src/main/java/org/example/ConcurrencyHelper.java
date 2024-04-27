package org.example;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyHelper {
    private final ExecutorService executor;
    public ConcurrencyHelper(int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void submitTask(Runnable task) {
        executor.submit(task);
    }

    public void shutdown() {
        executor.shutdown();
    }

}
