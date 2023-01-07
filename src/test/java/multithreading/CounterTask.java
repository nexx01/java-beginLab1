package multithreading;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//1. Создать класс Counter с одним полем:
//private int count;
//
//Добавить методы:
//- getCount() - для получение поля count
//- increment() - для увеличения значения поля на единицу
//- decrement() - для уменьшения значения поля на единицу
//
//2. Создать класс CounterThread c одним полем и конструктором для инициализации:
//private Counter counter;
//
//В методе run этого класса в цикле 100 раз вызвать counter.increment()
//
//3. В методе main создать 4 потока типа CounterThread,
//передав один и тот же объект Counter и запустить их, дождаться выполнения
//и вывести на консоль в текущее значение нашего счетчика:
//counter.getCount()
public class CounterTask {

    /**
     * 3. В методе main создать 4 потока типа CounterThread,
     * передав один и тот же объект Counter и запустить их, дождаться выполнения
     * и вывести на консоль в текущее значение нашего счетчика:
     * counter.getCount()
     */
    @RepeatedTest(100)
    void main() {
        Counter counter=new Counter();
        CounterThread counterThread1 = new CounterThread(counter);
        CounterThread counterThread2 = new CounterThread(counter);
        CounterThread counterThread3 = new CounterThread(counter);
        CounterThread counterThread4 = new CounterThread(counter);

        counterThread1.start();
        counterThread4.start();
        counterThread2.start();
        counterThread3.start();

        try {
            counterThread1.join();
            counterThread2.join();
            counterThread3.join();
            counterThread4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(counter.getCount());
        assertEquals(12000, counter.getCount());
    }
}

/**
 * 1. Создать класс Counter с одним полем:
 * private int count;
 * <p>
 * Добавить методы:
 * - getCount() - для получение поля count
 * - increment() - для увеличения значения поля на единицу
 * - decrement() - для уменьшения значения поля на единицу
 */

class Counter {
    private int count;

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int getCount() {
        return count;
    }


}
/**

2. Создать класс CounterThread c одним полем и конструктором для инициализации:
private Counter counter;

В методе run этого класса в цикле 100 раз вызвать counter.increment()
*/

class CounterThread extends Thread {

    private final Counter counter;

    public CounterThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3000; i++) {
            counter.increment();
        }
    }
}




