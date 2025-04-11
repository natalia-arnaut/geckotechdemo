package com.geckotech.assessment.integration.world;

import com.geckotech.assessment.infrastructure.http.payload.RecipePayload;
import io.cucumber.spring.CucumberTestContext;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;

@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public record ScenarioRecipe(
    Integer id,
    String name,
    String description,
    String cookTime,
    String type,
    HashMap<String, String> ingredients
) {
    public RecipePayload toPayload() {
        return new RecipePayload(
            name,
            description,
            cookTime,
            type,
            ingredients
        );
    }

    public String toResponseString() {
    return String.format(
            """
                {
                  "id": %d,
                  "name": "%s",
                  "description": "%s",
                  "cookTime": "%s",
                  "type": "%s",
                  "ingredients": %s
                }
            """.stripIndent().replace("\n", "").replace(" ", ""),
            id,
            name,
            description,
            cookTime,
            type,
            ingredientsToJsonString()
        );
    }

    private String ingredientsToJsonString() {
        var result = new StringBuilder();
        ingredients.forEach(
            (key, value) -> {
                result.append(String.format("\"%s\": \"%s\",", key, value));
            }
        );
        return "{" + result.toString().replaceAll(",$", "") + "}";
    }
}
