package multithreading.concurrent;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcurrentCollectionsDemo {

    @Test
    void main() throws InterruptedException {
        ArrayBlockingQueue<CashBox> cashBoxes = new ArrayBlockingQueue<>(2, false, List.of(new CashBox(), new CashBox()));


        List<Thread> collect = Stream.of(
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes),
                        new BuyerThread(cashBoxes)
                )
                .map(Thread::new)
                .peek(Thread::start)
                .collect(Collectors.toList());

        for (Thread thread :
                collect) {
            thread.join();
        }
    }
}

class BuyerThread implements Runnable {
    private final BlockingQueue<CashBox> cashBoxes;

    BuyerThread(BlockingQueue<CashBox> cashBoxes) {
        this.cashBoxes = cashBoxes;
    }

    @Override
    public void run() {
        try {
            CashBox take = cashBoxes.take();
            System.out.println(Thread.currentThread().getName() + " обслуживается на кассе " + take);

            Thread.sleep(4L);
            cashBoxes.put(take);
            System.out.println(Thread.currentThread().getName() + " освождаю кассу " + take);

        }   catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

}

class CashBox {

    private static int generator = 1;

    private int id;

    public CashBox() {
        this.id = generator++;
    }

    @Override
    public String toString() {
        return "Cashbox{" +
                "id=" + id +
                '}';
    }
}