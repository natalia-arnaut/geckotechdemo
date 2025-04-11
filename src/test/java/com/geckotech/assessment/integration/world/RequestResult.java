package com.geckotech.assessment.integration.world;

import io.cucumber.spring.CucumberTestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public class RequestResult {
    public MvcResult lastMvcResult;

    public void setLastMvcResult(MvcResult lastMvcResult) {
        this.lastMvcResult = lastMvcResult;
    }

    public MvcResult getLastMvcResult() {
        return lastMvcResult;
    }
}
