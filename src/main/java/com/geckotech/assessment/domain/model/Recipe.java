package com.geckotech.assessment.domain.model;

import java.sql.Time;
import java.util.HashMap;

public record Recipe(
    Integer id,
    String name,
    String description,
    Time cookTime,
    String cookTimeCustom,
    String type,
    HashMap<String, String> ingredients
) {
    public String cookTimeString() {
        String cookTimeString = cookTime.toString();
        return (
            cookTimeString.substring(0, cookTimeString.length() - 3) +
            (cookTimeCustom == null ? "" : (" " + cookTimeCustom))
        ).stripLeading();
    }
}
