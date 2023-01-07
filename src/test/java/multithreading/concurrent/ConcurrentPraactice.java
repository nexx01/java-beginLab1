package multithreading.concurrent;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

//1. Написать программу, бесконечно считывающую
//пользовательские числа из консоли.
//Эти числа представляют собой количество секунд.
//При каждом вводе числа, должна создаваться задача,
//которая засыпает на введённое число секунд и затем
//выводит "Я спал N секунд".
//Однако нужно сделать так, чтобы все задачи выполнялись в
//одном и том же потоке в порядке ввода.
//Т.е. в программе есть 2 потока: главный и поток для
//выполнения всех задач по очереди.
//При вводе отрицательного числа программа должна завершать свою работу.
//Второе решение - несколько потоков в пуле. Посчитать кол-во
//обработанных задач каждым потоком
//
//2. Задан массив случайных целых чисел (от 1 до 300)
//случайной длины (до 1 млн элементов).
//Найти максимальный элемент в массиве двумя способами: в
//одном потоке и используя 10 потоков.
//Сравнить затраченное в обоих случаях время.
public class ConcurrentPraactice {


    /**
     * 1. Написать программу, бесконечно считывающую
     * пользовательские числа из консоли.
     * Эти числа представляют собой количество секунд.
     * При каждом вводе числа, должна создаваться задача,
     * которая засыпает на введённое число секунд и затем
     * выводит "Я спал N секунд".
     * Однако нужно сделать так, чтобы все задачи выполнялись в
     * одном и том же потоке в порядке ввода.
     * Т.е. в программе есть 2 потока: главный и поток для
     * выполнения всех задач по очереди.
     * При вводе отрицательного числа программа должна завершать свою работу.
     * Второе решение - несколько потоков в пуле. Посчитать кол-во
     * обработанных задач каждым потоком
     */
    @Test
    public  void main() throws InterruptedException {
        String input = "1 1 1 1 2 3 1 -1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextInt()) {
            int seconds = scanner.nextInt();
            if (seconds < 0) {
                break;
            }
            executor.submit(()->{
                Thread.sleep(seconds * 1000L);
                System.out.format("\nПоток '%s' спал '%d' секунд", Thread.currentThread().getName(), seconds);
                return seconds;
            });
        }

        executor.shutdown();
        executor.awaitTermination(10L, TimeUnit.MINUTES);
    }


    /**
     * 1. Написать программу, бесконечно считывающую
     * пользовательские числа из консоли.
     * Эти числа представляют собой количество секунд.
     * При каждом вводе числа, должна создаваться задача,
     * которая засыпает на введённое число секунд и затем
     * выводит "Я спал N секунд".
     * Однако нужно сделать так, чтобы все задачи выполнялись в
     * одном и том же потоке в порядке ввода.
     * Т.е. в программе есть 2 потока: главный и поток для
     * выполнения всех задач по очереди.
     * При вводе отрицательного числа программа должна завершать свою работу.
     * Второе решение - несколько потоков в пуле. Посчитать кол-во
     * обработанных задач каждым потоком
     */
    @Test
    public  void task1_2() throws InterruptedException {
        String input = "1 1 1 1 2 3 1 2 4 4 1 2 2 3 -1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
//        Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextInt()) {
            int seconds = scanner.nextInt();
            if (seconds < 0) {
                break;
            }
            executor.submit(()->{
                Integer counter = threadLocal.get();
                threadLocal.set(counter == null ? 1 : ++counter);
                System.out.format("\nПоток '%s', задач '%d'",Thread.currentThread().getName(),threadLocal.get());
                Thread.sleep(seconds * 1000L);
                System.out.format("\nПоток '%s' спал '%d' секунд", Thread.currentThread().getName(), seconds);
                return seconds;
            });
        }

        executor.shutdown();
        executor.awaitTermination(10L, TimeUnit.MINUTES);
    }


    /**
     * 2. Задан массив случайных целых чисел (от 1 до 300)
     * случайной длины (до 1 млн элементов).
     * Найти максимальный элемент в массиве двумя способами: в
     * одном потоке и используя 10 потоков.
     * Сравнить затраченное в обоих случаях время.
     *
     * */
    @Test
    void task2() {
        int[] values = new int[100_000_000];
        Random random = new Random();
        for (int i = 0; i < values.length; i++) {
            values[i] = random.nextInt(300 + 1);
        }

        trackTime(() -> {
            try {
                return findMaxParallel(values,Executors.newFixedThreadPool(2));
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        trackTime(() -> findMax(values));
    }

    private static int trackTime(Supplier<Integer> supplier) {

        long startTime = System.currentTimeMillis();
        int result = supplier.get();
        System.out.println(result + ": " + (System.currentTimeMillis() - startTime));
        return result;
    }

    private static int findMax(int[] values) {
        return IntStream.of(values)
                .max()
                .orElse(Integer.MIN_VALUE);
    }

    private static int findMaxParallel(int[] values,ExecutorService executorService) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> IntStream.of(values)
                .parallel()
                .max()
                .orElse(Integer.MIN_VALUE)).get();
    }
}
