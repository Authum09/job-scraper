package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {
    public <T> T load (Path file, Class<T> type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (Files.exists(file)) {
            return objectMapper.readValue(file.toFile(), type);
        }
        throw new IOException("File does not exist. Please create a config.json.");
    }
}
