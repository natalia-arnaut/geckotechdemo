package com.geckotech.assessment.infrastructure.http.responseDto;

import java.util.HashMap;

public record Recipe(
    Integer id,
    String name,
    String description,
    String cookTime,
    String type,
    HashMap<String, String> ingredients
) {}
