package com.mysql.database.metadata.dto;

import java.util.List;

public class TableDbJsonData {

    private String name;
    private List<ColumnDataDto> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnDataDto> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDataDto> columns) {
        this.columns = columns;
    }
}
