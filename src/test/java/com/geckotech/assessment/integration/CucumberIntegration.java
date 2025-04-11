package com.geckotech.assessment.integration;

import org.junit.runners.Suite.SuiteClasses;
import org.springframework.context.annotation.ComponentScan;

@SuiteClasses(CucumberIntegration.class)
@ComponentScan(basePackages = {"com.geckotech.assessment.integration.glue", "com.geckotech.assessment.integration.util", "com.geckotech.assessment.integration.world"})
class CucumberIntegration {}
