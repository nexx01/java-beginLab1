package multithreading.vol;

import org.junit.jupiter.api.Test;

public class VolatileDemoTest {

    private static boolean flag = false;

    @Test
    void name() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!flag) {
                System.out.println("still false");
            }
        });

        Thread thread2 = new Thread(() -> {
            flag = true;
            System.out.println("flag is set");
        });

        thread.start();
        Thread.sleep(5L);
        thread2.start();
//
//        thread.join();
//        thread2.join();




    }
}
