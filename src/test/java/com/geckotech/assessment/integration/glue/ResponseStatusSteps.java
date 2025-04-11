package com.geckotech.assessment.integration.glue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geckotech.assessment.infrastructure.configuration.spring.jdbc.JdbcTemplateRecipe;
import com.geckotech.assessment.integration.AbstractStep;
import com.geckotech.assessment.integration.util.Requester;
import com.geckotech.assessment.integration.world.RequestResult;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberTestContext;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;
import java.util.Objects;

@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public class ResponseStatusSteps extends AbstractStep {
    private final RequestResult requestResult;
    private final Requester requester;
    JdbcTemplateRecipe jdbcTemplate;
    ObjectMapper objectMapper;

    public ResponseStatusSteps(
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

    @Then("the response should be NOT FOUND")
    public void theResponseShouldBeNotFound() {
        Assert.assertEquals(
            HttpServletResponse.SC_NOT_FOUND,
            Objects.requireNonNull(requestResult.getLastMvcResult()).getResponse().getStatus()
        );
    }

    @Then("the response should be OK")
    public void theResponseShouldBeOk() {
        Assert.assertEquals(
            HttpServletResponse.SC_OK,
            Objects.requireNonNull(requestResult.getLastMvcResult()).getResponse().getStatus()
        );
    }

    @Then("the response should be NO CONTENT")
    public void theResponseShouldBeNoContent() {
        Assert.assertEquals(
            HttpServletResponse.SC_NO_CONTENT,
            Objects.requireNonNull(requestResult.getLastMvcResult()).getResponse().getStatus()
        );
    }
}
