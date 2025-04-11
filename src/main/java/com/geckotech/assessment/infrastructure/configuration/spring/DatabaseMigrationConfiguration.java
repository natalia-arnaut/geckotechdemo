package com.geckotech.assessment.infrastructure.configuration.spring;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseMigrationConfiguration {
    @Bean(initMethod = "migrate")
    public Flyway flyway(@Qualifier("dataSourceRecipe") DataSource dataSource) {
        return Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource(dataSource)
            .locations("classpath:db/migration/cv/mysql")
            .cleanDisabled(false)
            .load();
    }
}
