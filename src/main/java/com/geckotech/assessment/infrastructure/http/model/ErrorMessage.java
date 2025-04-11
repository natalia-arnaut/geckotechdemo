package com.geckotech.assessment.infrastructure.http.model;

import java.util.HashMap;
import java.util.List;

public record ErrorMessage(String status, List<String> messages) {
    public ErrorMessage(String status, String message) {
        this(status, List.of(message));
    }

    public ErrorMessage(String status, HashMap<String, String> messages) {
        this(status, messages.toString());
    }
}
