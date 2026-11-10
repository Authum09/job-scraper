package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigLoader {
    public SearchCriteria buildSearchCriteria (Path file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(file.toFile(), SearchCriteria.class);
    }
}
