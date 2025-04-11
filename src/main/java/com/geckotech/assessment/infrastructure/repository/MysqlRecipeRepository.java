package com.geckotech.assessment.infrastructure.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geckotech.assessment.domain.model.Recipe;
import com.geckotech.assessment.domain.repository.RecipeRepository;
import com.geckotech.assessment.domain.repository.exception.SaveFailed;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class MysqlRecipeRepository implements RecipeRepository {
    private NamedParameterJdbcTemplate jdbcNamedTemplate;
    private ObjectMapper mapper;

    private RowMapper<Recipe> rowMapper = new RowMapper<>() {
        public Recipe mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return this.mapRecipe(resultSet);
        }

        private Recipe mapRecipe(ResultSet resultSet) throws SQLException {
            HashMap<String, String> ingredients;
            try {
                ingredients = mapper.readValue(resultSet.getString("ingredients"), HashMap.class);
            } catch (Exception e) {
                ingredients = new HashMap<>();
            }

            return new Recipe(
                Integer.valueOf(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getTime("cook_time"),
                resultSet.getString("cook_time_custom"),
                resultSet.getString("type"),
                ingredients
            );
        }
    };

    public MysqlRecipeRepository(
        NamedParameterJdbcTemplate jdbcNamedTemplate,
        ObjectMapper mapper
    ) {
        this.jdbcNamedTemplate = jdbcNamedTemplate;
        this.mapper = mapper;
    }

    @Override
    public Recipe getById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        try {
            return jdbcNamedTemplate.queryForObject(
                "SELECT id, name, description, cook_time, cook_time_custom, type, ingredients FROM recipe WHERE id = :id;",
                parameters,
                rowMapper
            );
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Recipe> getByName(String name) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", name);

        return jdbcNamedTemplate.query(
        """
            SELECT id, name, description, cook_time, cook_time_custom, type, ingredients 
            FROM recipe 
            WHERE name LIKE CONCAT('%', :name, '%')
            LIMIT 1000;
        """.stripIndent(),
            parameters,
            rowMapper
        );
    }

    @Override
    public List<Recipe> getAll() { //todo add pagination
        return jdbcNamedTemplate.query(
            "SELECT id, name, description, cook_time, cook_time_custom, type, ingredients FROM recipe WHERE 1 LIMIT 1000;",
            new MapSqlParameterSource(),
            rowMapper
        );
    }

    @Override
    public void add(Recipe entity) {
        try {
            jdbcNamedTemplate.update(
            """
                INSERT INTO recipe (name, description, cook_time, cook_time_custom, type, ingredients)
                VALUES (:name, :description, IF(:cookTime='', null, :cookTime), IF(:cookTimeCustom='', null, :cookTimeCustom), :type, :ingredients);
            """.stripIndent(),
                recipeToParameter(entity)
            );
        } catch (Exception e) {
            throw new SaveFailed(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void remove(Recipe entity) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", entity.id());

        jdbcNamedTemplate.update("DELETE FROM recipe WHERE id = :id", parameters);
    }

    private MapSqlParameterSource recipeToParameter(Recipe recipe) throws JsonProcessingException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", recipe.name());
        parameters.addValue("description", recipe.description());
        parameters.addValue("type", recipe.type());
        parameters.addValue("cookTime", recipe.cookTime().toString());
        parameters.addValue("cookTimeCustom", recipe.cookTimeCustom());
        parameters.addValue("ingredients", mapper.writeValueAsString(recipe.ingredients()));

        return parameters;
    }
}
