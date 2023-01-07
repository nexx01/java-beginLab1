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
public class SynkSynkCounterTask {

    /**
     * 3. В методе main создать 4 потока типа CounterThread,
     * передав один и тот же объект Counter и запустить их, дождаться выполнения
     * и вывести на консоль в текущее значение нашего счетчика:
     * counter.getCount()
     */
    @RepeatedTest(10)
    void main() {
        SynkCounter counter=new SynkCounter();
        SynkCounterThread counterThread1 = new SynkCounterThread(counter);
        SynkCounterThread counterThread2 = new SynkCounterThread(counter);
        SynkCounterThread counterThread3 = new SynkCounterThread(counter);
        SynkCounterThread counterThread4 = new SynkCounterThread(counter);
        SynkCounterThread counterThread5 = new SynkCounterThread(counter);
        SynkCounterThread counterThread6 = new SynkCounterThread(counter);
        SynkCounterThread counterThread7 = new SynkCounterThread(counter);

        counterThread1.start();
        counterThread2.start();
        counterThread3.start();
        counterThread4.start();
        counterThread5.start();
        counterThread6.start();
        counterThread7.start();

        try {
            counterThread1.join();
            counterThread2.join();
            counterThread3.join();
            counterThread4.join();
            counterThread5.join();
            counterThread6.join();
            counterThread7.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(counter.getCount());
        assertEquals(2100, counter.getCount());
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

class SynkCounter {
    private int count;

    public void increment() {
        synchronized (this) {
            count++;
        };
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

class SynkCounterThread extends Thread {

    private final SynkCounter counter;

    public SynkCounterThread(SynkCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {
            counter.increment();
        }
    }
}




