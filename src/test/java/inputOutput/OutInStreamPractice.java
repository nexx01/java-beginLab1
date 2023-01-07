package inputOutput;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

//es (14 sloc)  1.16 KB
//1. Задан файл с текстом, найти и вывести в консоль все слова,
//начинающиеся с гласной буквы.
//
//2. Задан файл с текстом, найти и вывести в консоль все слова,  для
//которых последняя буква одного слова совпадает с первой буквой
//следующего слова from resources/text.txt
//
//3. Задан файл с текстом. В каждой строке найти и вывести
//наибольшее число цифр, идущих подряд.
//
//4. Задан файл с java-кодом. Прочитать текст программы из файла и
//все слова public в объявлении атрибутов и методов класса заменить
//на слово private. Результат сохранить в другой заранее созданный
//файл.
//
//5. Задан файл с java-кодом. Прочитать текст программы из файла и
//записать в другой файл в обратном порядке символы каждой
//строки.
public class OutInStreamPractice {

    /**
     * 1. Задан файл с текстом, найти и вывести в консоль все слова,
     * начинающиеся с гласной буквы.
     */
    @Test
    void firstTask() throws IOException {

        String VOWELS = "уеуоаыяэ";

        Path path = Path.of("resources", "test.txt");
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                char firstSymbol = word.charAt(0);
                if (VOWELS.indexOf(firstSymbol) != -1) {
                    System.out.println(word);
                }
            }
        }
    }

    /**
     * 2. Задан файл с текстом, найти и вывести в консоль все слова,  для
     * которых последняя буква одного слова совпадает с первой буквой
     * следующего слова from resources/text.txt
     **/
    @Test
    void secondTask() throws IOException {
        Path path = Path.of("resources", "test.txt");
        try (Scanner scanner = new Scanner(path)) {
            String prev = null;
            if (scanner.hasNext()) {
                prev = scanner.next();
            }
            while (scanner.hasNext()) {
                String current = scanner.next();
                if (isEqualLastSymbolAndFirstSymbol(prev, current)) {
                    System.out.println(prev + " " + current);
                }
                prev = current;
            }
        }
    }

    /**
     * 3. Задан файл с текстом. В каждой строке найти и вывести
     * наибольшее число цифр, идущих подряд.
     */
    @Test
    void thirdTask() throws IOException {

        Path path = Path.of("resources", "test.txt");
        Files.readAllLines(path).stream()
                .map(F -> digitsCount(F))
                .forEach(System.out::println);
    }

    /**
     * 4. Задан файл с java-кодом. Прочитать текст программы из файла и
     * все слова public в объявлении атрибутов и методов класса заменить
     * на слово private. Результат сохранить в другой заранее созданный
     * файл.
     */
    @Test
    void thourthTask() throws IOException {
        Path path = Path.of("src", "test", "java", "inputOutput", "FileRunner.java");
        String string = Files.readString(path);
        String resultString = string.replace("public", "private");
        Path resulPath = Path.of("resources", "FileRunner.java");
        Files.writeString(resulPath, resultString);
    }

    /**
     * 5. Задан файл с java-кодом. Прочитать текст программы из файла и
     * записать в другой файл в обратном порядке символы каждой
     * строки.
     */
    @Test
    void fifthTask() throws IOException {
        Path path = Path.of("src", "test", "java", "inputOutput", "OutputStreamTest.java");
        Path result = Path.of("resources", "OutputStream.java");
        try (Stream<String> stringStream = Files.lines(path);
            BufferedWriter bufferedWriter = Files.newBufferedWriter(result, APPEND, CREATE)) {
            stringStream
                    .map(StringBuilder::new)
                    .map(StringBuilder::reverse)
                    .forEach(line->{
                        try {
                            bufferedWriter.write(line.toString());
                            bufferedWriter.newLine();
                        } catch (IOException e) {
                             e.printStackTrace();
                        }
                    });
        }
    }

    private Integer digitsCount(String line) {
        int result = 0;
        int counter = 0;
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                counter++;
            } else {
                if (result < counter) {
                    result = counter;
                }
                counter = 0;
            }
        }
        return result;
    }

    private boolean isEqualLastSymbolAndFirstSymbol(String prev, String current) {
        return prev.charAt(prev.length() - 1) == current.charAt(0);
    }
}
