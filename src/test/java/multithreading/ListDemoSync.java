package multithreading;

import multithreading.ListThreadSync.ListThreadSync2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListDemoSync{

    @Test
    void main() throws InterruptedException {
        List<Integer> integers = Collections.synchronizedList(new ArrayList<>());

        ListThreadSync listThread1 = new ListThreadSync(integers);
        ListThreadSync listThread2 = new ListThreadSync(integers);
        ListThreadSync listThread3 = new ListThreadSync(integers);
        ListThreadSync listThread4 = new ListThreadSync(integers);
        ListThreadSync listThread5 = new ListThreadSync(integers);
        ListThreadSync listThread6 = new ListThreadSync(integers);
        ListThreadSync listThread7 = new ListThreadSync(integers);
        ListThreadSync listThread8 = new ListThreadSync(integers);

        ListThreadSync2 listThreadSync2 = new ListThreadSync2(integers);

        listThread1.start();
        listThread2.start();
        listThread3.start();
        listThread4.start();
        listThread5.start();
        listThread6.start();
        listThread7.start();
        listThread8.start();
        listThreadSync2.start();


        listThread1.join();
        listThread2.join();
        listThread3.join();
        listThread4.join();
        listThread5.join();
        listThread6.join();
        listThread7.join();
        listThread8.join();
        listThreadSync2.join();

        System.out.println(integers);
    }
}

class ListThreadSync extends Thread {
    private List<Integer> list;

    public ListThreadSync(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 400; i++) {
                list.add(i);
            }
        }
    }

    static class ListThreadSync2 extends Thread {
        private List<Integer> list;

        public ListThreadSync2(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        }

    }
}