package com.geckotech.assessment.integration;

import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;

public abstract class AbstractStep {
    public List<Map<String, String>> dataTableMapper(DataTable rows) {
        return rows.asMaps(String.class, String.class);
    }
}
