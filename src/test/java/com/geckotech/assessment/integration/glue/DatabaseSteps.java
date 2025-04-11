package com.geckotech.assessment.integration.glue;

import com.geckotech.assessment.integration.AbstractStep;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class DatabaseSteps extends AbstractStep {
    private final List<Flyway> flyways;
    private final DataSourceTransactionManager transactionManager;
    private TransactionStatus transaction;

    private static boolean beforeAllExecuted = false;

    public DatabaseSteps(
        List<Flyway> flyways,
        @Qualifier("transactionManagerRecipe") DataSourceTransactionManager transactionManager
    ) {
        this.flyways = flyways;
        this.transactionManager = transactionManager;
    }

    private void beforeAll() {
        for (Flyway flyway : flyways) {
            flyway.clean();
            flyway.migrate();
        }
    }

    @Before(order = 1)
    public void beforeEach() {
        if (!beforeAllExecuted) {
            beforeAllExecuted = true;
            beforeAll();
        }
        transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @After
    public void afterEach() {
        transactionManager.rollback(transaction);
    }
}
