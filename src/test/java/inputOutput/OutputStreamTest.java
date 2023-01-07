package inputOutput;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;

public class OutputStreamTest {

    @Test
    void name() throws IOException {
        File file = Path.of("resources", "output.txt").toFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file,true)) {
            String s = "Hello world! - 2\n";
            fileOutputStream.write(s.getBytes());
        }
    }

    @Test
    void withBuffered() throws IOException {
        File file = Path.of("resources", "outputWithBufferd").toFile();
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file,true))) {
            String s = "Hello world!0-3";
            bufferedOutputStream.write(s.getBytes());
            bufferedOutputStream.write(System.lineSeparator().getBytes());
        }
    }

    @Test
    void writerTest() throws IOException {
        File file = Path.of("resources", "writer.poem").toFile();
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.append("Hello world!");
            fileWriter.append("Java");
        }
    }

    @Test
    void writerWitBuffered() throws IOException {

        File file = Path.of("resources", "writerBuffered.poem").toFile();

        try (BufferedWriter bufferedOutputStream = new BufferedWriter(new FileWriter(file))) {
            bufferedOutputStream.append("Hello world!");
            bufferedOutputStream.newLine();
            bufferedOutputStream.append("Java!");
        }

    }
}
