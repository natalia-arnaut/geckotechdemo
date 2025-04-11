package com.geckotech.assessment.domain.repository;

import com.geckotech.assessment.domain.model.Recipe;
import com.geckotech.assessment.domain.repository.exception.SaveFailed;

import java.util.List;

public interface RecipeRepository {
    Recipe getById(Integer id);
    List<Recipe> getByName(String name);
    List<Recipe> getAll();
    void add(Recipe entity) throws SaveFailed;
    void remove(Recipe entity);
}
