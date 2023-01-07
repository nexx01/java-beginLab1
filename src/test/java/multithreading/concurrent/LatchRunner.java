package multithreading.concurrent;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LatchRunner {

    @Test
    void main() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(RocketDetail.values().length);
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

       threadPool.submit(new Rocket(latch));

        Arrays.stream(RocketDetail.values())
                .map(detail->new RocketDetailRunnable(latch,detail))
                .forEach(threadPool::submit);

        threadPool.shutdown();
        threadPool.awaitTermination(4, TimeUnit.HOURS);

    }
}

class RocketDetailRunnable implements Runnable{

    private final CountDownLatch countDownLatch;
    private  final RocketDetail rocketDetail;

    RocketDetailRunnable(CountDownLatch countDownLatch, RocketDetail rocketDetail) {
        this.countDownLatch = countDownLatch;
        this.rocketDetail = rocketDetail;
    }

    @Override
    public void run() {
        System.out.println("Готовиться деталь "+rocketDetail);
        try {
            Thread.sleep(1000L);
            countDownLatch.countDown();
            System.out.println("Деталь готова: "+ rocketDetail);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

enum RocketDetail{
    PART1,PART2,PART3,PART4,PART5;
}

class Rocket implements Runnable{

    private final CountDownLatch countDownLatch;

    Rocket(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Ракета готовится к запуску....");
        try {
            countDownLatch.await();
            System.out.println(" Пуск!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
