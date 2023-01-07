package multithreading.task;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class TaskTest {

    @Test
    void name() throws InterruptedException {

        LinkedList<Integer> queue = new LinkedList<>();
        Thread producerThread = new Thread(new ProducerThread(queue));
        Thread consumerTread = new Thread(new ConsumerTread(queue));

        producerThread.start();
        consumerTread.start();

        producerThread.join();
        consumerTread.join();

    }
}

class ConsumerTread extends Thread {

    private final Queue<Integer> list;

    public ConsumerTread(Queue<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        synchronized (list) {
            while (true) {
                if (!list.isEmpty()) {
                    Integer removeValue = list.remove();
                    System.out.println("Consumer get value: " + removeValue +" size = "+list.size());
                } else {
                    System.out.println("consumer is waiting,list is empty");

                }
                list.notifyAll();

                try {
                    int random = 30;
                    System.out.println("consumer wait: " + random);
                    list.wait(random);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class ProducerThread extends Thread {

    private final Queue<Integer> list;

    public ProducerThread(Queue<Integer> list) {

        this.list = list;
    }

    @Override
    public void run() {
        synchronized (list) {
            while (true) {
                if (list.size() < 10) {
                    int random = RandomUtil.getRandom();
                    list.add(random);
                    System.out.println("producer add value: " + random + " size - " + list.size());
                } else {
                    System.out.println("producer does nothing ");
                }
                list.notifyAll();
                try {
//                    Thread.sleep(RandomUtil.getRandom());

                    int random1 = 1;
                    System.out.println("producer wait: " + random1);
                    list.wait(random1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

    final class RandomUtil {
        private static final Random RANDOM = new Random();
        private static final int DEFAULT_BOUND = 10;

        private RandomUtil() {
        }

        public static int getRandom() {
            return RANDOM.nextInt(DEFAULT_BOUND);
        }

        public static int getRandom(int bound) {
            return RANDOM.nextInt(bound);
        }
    }

