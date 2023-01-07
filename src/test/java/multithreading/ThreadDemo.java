package multithreading;

import org.junit.jupiter.api.Test;

public class ThreadDemo {

    @Test
    void getName() {
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    void createThread() throws InterruptedException {
        class SimpleThread extends Thread {
            @Override
            public void run() {
                System.out.println("Hello "+getName());
            }
        }
        System.out.println(Thread.currentThread().getName());
        SimpleThread simpleThread1 = new SimpleThread();
        SimpleThread simpleThread2 = new SimpleThread();
        simpleThread1.run();
        simpleThread2.start();
        simpleThread1.start();
        simpleThread2.join();
        simpleThread1.join();

        System.out.println(Thread.currentThread().getName());
    }

    @Test
    void createThread2() {
        class SimpleRunnable implements Runnable {
            @Override
            public void run() {
                System.out.println("Hello from Runnable " + Thread.currentThread().getName());
            }
        }

        SimpleRunnable simpleRunnable = new SimpleRunnable();
        Thread thread = new Thread(simpleRunnable);
        thread.start();
    }

    @Test
    void stateOfThread() throws InterruptedException {
        Thread hello_from_thread = new Thread(() -> System.out.println("hello from thread"+ " Thread state in " +
                "runnable is "+Thread.currentThread().getState()));
        System.out.println("Thread stats is "+hello_from_thread.getState());
        hello_from_thread.start();
        System.out.println("Thread stats after start is " + hello_from_thread.getState());

        hello_from_thread.join();
        System.out.println("Thread stats after join is " + hello_from_thread.getState());
    }


}
