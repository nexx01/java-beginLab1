package multithreading.concurrent;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.*;

public class ThreadPoolDemo {

    @Test
    void main() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("OK"), 2L, 4L, TimeUnit.SECONDS);


        Thread.sleep(20000L);
//        scheduledExecutorService.awaitTermination(1L, TimeUnit.HOURS);
    }

    void test() throws InterruptedException, ExecutionException {
//        Executors.newSingleThreadExecutor();
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
//        Executors.newCachedThreadPool();
//
//        Executors.newScheduledThreadPool(5);
//        Executors.newWorkStealingPool();

        Future<Integer> future = threadPool.submit(() -> {
            Thread.sleep(2000L);
            System.out.println("It's callable");
            return 1;
        });

//        CompletableFuture.supplyAsync()

        System.out.println("Result: "+ future.get());
        threadPool.shutdown();
        threadPool.awaitTermination(1L, TimeUnit.SECONDS);
        System.out.println("main end");
    }
}

class PoolThread extends Thread {

    private final Queue<Runnable> tasks;

    public PoolThread(Queue<Runnable> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (true) {
            Optional<Runnable> task= Optional.empty();
            synchronized (tasks) {
                if (!tasks.isEmpty()) {
                    task = Optional.of(tasks.remove());
                }
            }
            task.ifPresent(Runnable::run);
        }
    }
}
