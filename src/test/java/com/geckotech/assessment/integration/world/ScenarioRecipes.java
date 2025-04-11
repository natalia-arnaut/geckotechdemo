package com.geckotech.assessment.integration.world;

import io.cucumber.spring.CucumberTestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public class ScenarioRecipes {
    public HashMap<String, ScenarioRecipe> nameToCvMap = new HashMap<>();
}
