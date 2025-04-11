package com.geckotech.assessment.infrastructure.configuration.spring.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Primary
@Component
public class JdbcTemplateRecipe extends JdbcTemplate {
    public JdbcTemplateRecipe (@Qualifier("dataSourceRecipe") DataSource dataSource) {
        super(dataSource);
    }
}
