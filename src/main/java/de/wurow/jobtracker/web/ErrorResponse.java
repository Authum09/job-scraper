package de.wurow.jobtracker.web;

import java.util.Map;

public record ErrorResponse(int status, String error, Map<String,String> fieldErrors) {
    public ErrorResponse(int status, String error) {
        this(status, error, Map.of());
    }
}
