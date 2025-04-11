package com.geckotech.assessment.integration.glue;

import com.geckotech.assessment.infrastructure.RecipeApplication;
import com.geckotech.assessment.integration.AbstractStep;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(
    properties = {"spring.flyway.clean-disabled=false"},
    classes = {RecipeApplication.class}
)
@WebAppConfiguration
@CucumberContextConfiguration
//@ComponentScan( // todo split test scope
//    basePackages = {"com.geckotech.assessment.integration"},
//    useDefaultFilters = false
//)
public class BootstrapSteps extends AbstractStep {
    @Before(order = 2)
    public void setUp() {}
}
