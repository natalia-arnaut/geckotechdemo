package com.geckotech.assessment.infrastructure.configuration.spring.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Primary
@Component
public class NamedParameterJdbcTemplateRecipe extends NamedParameterJdbcTemplate {
    public NamedParameterJdbcTemplateRecipe(@Qualifier("dataSourceRecipe") DataSource dataSource) {
        super(dataSource);
    }
}
