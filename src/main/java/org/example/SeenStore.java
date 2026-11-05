package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;


public class SeenStore {

    private static final Path FILE_PATH = Path.of("seen.txt");

    public Set<String> load() throws IOException {
        if (!Files.exists(FILE_PATH)) {
            return new HashSet<>();
        }

        return new HashSet<>(Files.readAllLines(FILE_PATH));


    }

    public void save(Set<String> referenceNumbers) throws IOException {
        Files.write(FILE_PATH, referenceNumbers);
    }
}
