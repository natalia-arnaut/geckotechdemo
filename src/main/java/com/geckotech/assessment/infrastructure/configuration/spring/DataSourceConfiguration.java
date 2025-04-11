package com.geckotech.assessment.infrastructure.configuration.spring;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Bean(name = {"dataSourceRecipe"})
    public DataSource dataSourceRecipe() {
        return new HikariDataSource(dataSourceRecipeProperties());
    }

    @Bean
    @ConfigurationProperties("app.datasource.recipe")
    public HikariConfig dataSourceRecipeProperties() { return new HikariConfig(); }
}

