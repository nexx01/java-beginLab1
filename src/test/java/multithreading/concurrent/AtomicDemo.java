package multithreading.concurrent;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {

    @Test
    void name() {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
        System.out.println(atomicInteger);
        int newValue = atomicInteger.addAndGet(20);
        System.out.println(newValue);

//        Unsafe unsafe = Unsafe.getUnsafe();
//        System.out.println(unsafe.addressSize());
    }
}
