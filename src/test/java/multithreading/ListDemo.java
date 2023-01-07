package multithreading;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ListDemo {
    @Test
    void main() throws InterruptedException {
        List<Integer> integers = new ArrayList<>();

        ListThread listThread1 = new ListThread(integers);
        ListThread listThread2 = new ListThread(integers);
        ListThread listThread3 = new ListThread(integers);
        ListThread listThread4 = new ListThread(integers);
        ListThread listThread5 = new ListThread(integers);
        ListThread listThread6 = new ListThread(integers);
        ListThread listThread7 = new ListThread(integers);
        ListThread listThread8 = new ListThread(integers);

        listThread1.start();
        listThread2.start();
        listThread3.start();
        listThread4.start();
        listThread5.start();
        listThread6.start();
        listThread7.start();
        listThread8.start();


        listThread1.join();
        listThread2.join();
        listThread3.join();
        listThread4.join();
        listThread5.join();
        listThread6.join();
        listThread7.join();
        listThread8.join();

        System.out.println(integers);
    }
}

class ListThread extends Thread {
    private List<Integer> list;

    public ListThread(List<Integer> list) {
        this.list = list;
    }



    @Override
    public void run() {
        for (int i = 0; i < 400; i++) {
            list.add(i);
        }
    }
}
