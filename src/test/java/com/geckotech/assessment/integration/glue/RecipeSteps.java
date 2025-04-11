package com.geckotech.assessment.integration.glue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geckotech.assessment.infrastructure.configuration.spring.jdbc.JdbcTemplateRecipe;
import com.geckotech.assessment.infrastructure.http.payload.RecipeFilterPayload;
import com.geckotech.assessment.infrastructure.http.responseDto.Recipe;
import com.geckotech.assessment.integration.AbstractStep;
import com.geckotech.assessment.integration.util.Requester;
import com.geckotech.assessment.integration.world.RequestResult;
import com.geckotech.assessment.integration.world.ScenarioRecipe;
import com.geckotech.assessment.integration.world.ScenarioRecipes;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberTestContext;
import org.springframework.context.annotation.Scope;
import org.junit.Assert;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public class RecipeSteps extends AbstractStep {
    private final RequestResult requestResult;
    private final Requester requester;
    JdbcTemplateRecipe jdbcTemplate;
    ObjectMapper objectMapper;
    private ScenarioRecipes scenarioRecipes;

    {
        scenarioRecipes = new ScenarioRecipes();
        scenarioRecipes.nameToCvMap.put(
            "Pancake", new ScenarioRecipe(
                1,
                // todo change id to UUID or add test jdbc method to insert with ids
                "Pancake",
                "Pancake description",
                "00:30",
                "breakfast",
                new HashMap<>()
            )
        );
        scenarioRecipes.nameToCvMap.put(
            "French Pancake", new ScenarioRecipe(
                3,
                "French Pancake",
                "Description des crepes",
                "00:30",
                "lunch",
                new HashMap<>()
            )
        );
        scenarioRecipes.nameToCvMap.put(
            "Salad", new ScenarioRecipe(
                2,
                "Salad",
                "Salad description",
                "00:30",
                "dinner",
                new HashMap<>()
            )
        );
        scenarioRecipes.nameToCvMap.put(
            "Borshch", new ScenarioRecipe(
                4,
                "Salad",
                "Salad with water description",
                "01:30",
                "dinner",
                new HashMap<>()
            )
        );
    }

    public RecipeSteps(
        RequestResult requestResult,
        Requester requester,
        JdbcTemplateRecipe jdbcTemplate,
        ObjectMapper objectMapper
    ) {
        this.requestResult = requestResult;
        this.requester = requester;
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @When("I request recipe {string} by id")
    public void iRequestRecipeById(String recipeName) throws Exception {
        requestResult.setLastMvcResult(requester.jsonGet(
            String.format("/v3/recipe/" + scenarioRecipes.nameToCvMap.get(recipeName).id()),
            ""
        ));
    }

    @When("I request all recipes")
    public void iRequestAllRecipes() throws Exception {
        requestResult.setLastMvcResult(requester.jsonGet(
            "/v3/recipe/overview",
            ""
        ));
    }

    @Then("the response should be empty list")
    public void theResponseShouldBeOk() throws UnsupportedEncodingException {
        Assert.assertEquals(
            "[]",
            Objects.requireNonNull(requestResult.getLastMvcResult()).getResponse().getContentAsString()
        );
    }

    @Given("I add a recipe {string}")
    public void iAddRecipe(String recipeName) throws Exception {
        requestResult.setLastMvcResult(requester.jsonPost(
            "/v3/recipe",
            "",
            scenarioRecipes.nameToCvMap.get(recipeName).toPayload(),
            new HashMap<>()
        ));
    }

    @Then("the response should be one recipe {string}")
    public void theResponseShouldBeOneRecipe(String recipeName) throws Exception {
        Assert.assertEquals(
            scenarioRecipes.nameToCvMap.get(recipeName).toResponseString(),
            requestResult.getLastMvcResult().getResponse().getContentAsString()
        );
    }

    @Then("the response should contain recipes:")
    public void theResponseShouldContainRecipes(DataTable dataTable) throws Exception {
        String result = requestResult.getLastMvcResult().getResponse().getContentAsString();
        Assert.assertNotEquals(0, result.length());
        List<Recipe> recipesResponse  =  objectMapper.readValue(
            result,
            new TypeReference<List<Recipe>>() {}
        );

        dataTable.asList().forEach(recipeName -> {
            Assert.assertTrue(
                recipesResponse
                    .stream().anyMatch(recipe -> recipeName.equals(recipe.name()))
            );
        });
    }

    @When("I remove recipe {string}")
    public void iRemoveRecipe(String recipeName) throws Exception {
        requestResult.setLastMvcResult(requester.jsonDelete(
            String.format("/v3/recipe/" + scenarioRecipes.nameToCvMap.get(recipeName).id()),
            ""
        ));
    }

    @When("I search for recipe {string}")
    public void iSearchForRecipe(String recipeName) throws Exception {
        requestResult.setLastMvcResult(requester.jsonPost(
            "/v3/recipe/search",
            "",
            new RecipeFilterPayload(recipeName),
            new HashMap<>()
        ));
    }
}
