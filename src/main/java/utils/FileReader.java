package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class FileReader {

    public static List<String> readFile(String name) {
        try {
            var filePath = Objects.requireNonNull(FileReader.class.getClassLoader().getResource("inputs/" + name)).toURI();
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException | URISyntaxException e) {
            return List.of();
        }
    }
}
