package multithreading.concurrent;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.*;

public class BarrierDemo {
    @Test
    void main() throws InterruptedException {
        CyclicBarrier latch = new CyclicBarrier(RocketDetailB.values().length,()->{
            System.out.println(" Пуск!!!");
        });
        ExecutorService threadPool = Executors.newCachedThreadPool();

//        threadPool.submit(new RocketB(latch));

        Arrays.stream(RocketDetailB.values())
                .map(detail->new RocketDetailRunnableB(latch,detail))
                .forEach(threadPool::submit);

        threadPool.shutdown();
        threadPool.awaitTermination(4, TimeUnit.HOURS);

    }
}

class RocketDetailRunnableB implements Runnable{

    private final CyclicBarrier barrier;
    private  final RocketDetailB rocketDetail;

    RocketDetailRunnableB(CyclicBarrier barrier, RocketDetailB rocketDetail) {
        this.barrier = barrier;
        this.rocketDetail = rocketDetail;
    }

    @Override
    public void run() {
        System.out.println("Готовиться деталь "+rocketDetail);
        try {
            Thread.sleep(1000L);
            System.out.println("Деталь готова и ожидает: "+ rocketDetail);
            barrier.await();
            System.out.println("Деталь использована: "+ rocketDetail);
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}

enum RocketDetailB {
    PART1,PART2,PART3,PART4,PART5;
}
//
//class RocketB implements Runnable{
//
//    private final CyclicBarrier barrier;
//
//    RocketB(CyclicBarrier barrier) {
//        this.barrier = barrier;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("Ракета готовится к запуску....");
//        try {
//            barrier.await();
//            System.out.println(" Пуск!!!");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}



