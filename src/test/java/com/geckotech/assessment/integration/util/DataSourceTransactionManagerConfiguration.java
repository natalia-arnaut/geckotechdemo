package com.geckotech.assessment.integration.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

@Configuration
public class DataSourceTransactionManagerConfiguration {
    @Bean(name = {"transactionManagerRecipe"})
    public DataSourceTransactionManager transactionManagerOrdering(
        @Qualifier("dataSourceRecipe") DataSource dataSource
    ) {
        return new DataSourceTransactionManager(dataSource);
    }
}
