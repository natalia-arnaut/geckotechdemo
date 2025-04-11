package com.geckotech.assessment.application.service;

import com.geckotech.assessment.domain.repository.RecipeRepository;
import com.geckotech.assessment.domain.repository.exception.SaveFailed;
import com.geckotech.assessment.infrastructure.http.payload.RecipeFilterPayload;
import com.geckotech.assessment.infrastructure.http.payload.RecipePayload;
import com.geckotech.assessment.infrastructure.http.responseDto.Recipe;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RecipeService {
    private final RecipeRepository recipeRepository;

    RecipeService(RecipeRepository RecipeRepository) {
        this.recipeRepository = RecipeRepository;
    }

    public List<Recipe> getAll() {
        return recipeRepository.getAll().stream().map(this::toResponseDto).toList();
    }

    public Recipe getRecipe(Integer id) {
        if (id != null) {
            return toResponseDto(recipeRepository.getById(id));
        }

        return null;
    }

    public List<Recipe> search(RecipeFilterPayload filter) {
        if (filter.name() != null && !filter.name().isEmpty()) {
            return recipeRepository.getByName(filter.name()).stream().map(this::toResponseDto).collect(Collectors.toList());
        }

        return null;
    }

    // todo - should it be removed to Controller or other service?
    private Recipe toResponseDto(com.geckotech.assessment.domain.model.Recipe r) {
        if (r == null) { return null; }
        return new Recipe(r.id(), r.name(), r.description(), r.cookTimeString(), r.type(), r.ingredients());
    }

    public void remove(Integer id) {
        com.geckotech.assessment.domain.model.Recipe foundRecipe = null;
        if (id != null) {
            foundRecipe = recipeRepository.getById(id);
        }

        if (foundRecipe != null) {
            recipeRepository.remove(foundRecipe);
        }
    }

    // todo move validation to some Validator service
    public void save(RecipePayload recipePayload) throws SaveFailed {
        java.sql.Time cookTimeTime;
        String cookTime;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            long ms = sdf.parse(recipePayload.cookTime()).getTime();
            cookTimeTime = new java.sql.Time(ms);
            cookTime = null;
        } catch (ParseException e) {
            cookTimeTime = null;
            cookTime = recipePayload.cookTime();
        }

        recipeRepository.add(
            new com.geckotech.assessment.domain.model.Recipe(
                null,
                recipePayload.name(),
                recipePayload.description(),
                cookTimeTime,
                cookTime,
                recipePayload.type(),
                recipePayload.ingredients()
            )
        );
    }
}
