package com.geckotech.assessment.infrastructure.http.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;

@Schema(defaultValue = """
    {
      "name": "string",
      "description": "string",
      "cookTime": "00:30 (hours:minutes), or free form",
      "type": "breakfast, lunch or dinner",
      "ingredients": {
        "ingredient 1": "some amount",
        "ingredient 2": "some amount",
        "ingredient 3": "some amount"
      }
    }
    """)
public record RecipePayload(
    String name,
    String description,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    String cookTime,
    String type,
    HashMap<String, String> ingredients
) {}
