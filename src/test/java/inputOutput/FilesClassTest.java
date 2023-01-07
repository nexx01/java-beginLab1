package inputOutput;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FilesClassTest {

    @Test
    void bufferedWriter() throws IOException {

        Path path = Path.of("resources", "writer.poem");
        try (BufferedWriter writer
                     = Files.newBufferedWriter(path)) {

            writer.newLine();
            writer.append("Hello world! with help of Files");
            writer.newLine();
            writer.append("Java");
        }
    }

    @Test
    void filesWrite() throws IOException {

        Path of = Path.of("resources", "writer");
        Files.write(of, List.of("Hello world!", "Java"));
    }

    @Test
    void filesReader() throws IOException {
        Path path = Path.of("resources", "writer");
        List<String> strings = Files.readAllLines(path);

        strings.stream()
                .forEach(System.out::println);
    }

    @Test
    void filesResaderLines() throws IOException {
        Path path = Path.of("resources", "writer");
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(System.out::println);
        }
    }


}
